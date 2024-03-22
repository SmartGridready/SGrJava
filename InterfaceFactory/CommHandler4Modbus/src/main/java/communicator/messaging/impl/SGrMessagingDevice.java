package communicator.messaging.impl;

import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.InMessage;
import com.smartgridready.ns.v0.InterfaceList;
import com.smartgridready.ns.v0.MessageFilter;
import com.smartgridready.ns.v0.MessagingDataPoint;
import com.smartgridready.ns.v0.MessagingDataPointConfiguration;
import com.smartgridready.ns.v0.MessagingFunctionalProfile;
import com.smartgridready.ns.v0.MessagingInterface;
import com.smartgridready.ns.v0.MessagingInterfaceDescription;
import com.smartgridready.ns.v0.OutMessage;
import com.smartgridready.ns.v0.ResponseQuery;
import com.smartgridready.ns.v0.ResponseQueryType;
import communicator.common.api.StringValue;
import communicator.common.api.Value;
import communicator.common.helper.JsonHelper;
import communicator.common.impl.SGrDeviceBase;
import communicator.common.runtime.GenDriverException;
import communicator.messaging.api.GenMessagingApi;
import communicator.messaging.api.Message;
import communicator.messaging.client.MessagingClient;
import communicator.messaging.client.MessagingClientFactory;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class SGrMessagingDevice extends SGrDeviceBase<
        DeviceFrame,
        MessagingFunctionalProfile,
        MessagingDataPoint> implements GenMessagingApi {

    private static final Logger LOG = LoggerFactory.getLogger(SGrMessagingDevice.class);

    private static final long SYNC_READ_TIMEOUT_MSEC = 60000;

    private final MessagingClient messagingClient;

    private final Map<MessageCacheKey, MessageCacheRecord> messageCache = new HashMap<>();

    public SGrMessagingDevice(DeviceFrame deviceDescription) throws GenDriverException {
        super(deviceDescription);

        MessagingInterfaceDescription ifDesc = Optional.ofNullable(deviceDescription.getInterfaceList())
                .map(InterfaceList::getMessagingInterface)
                .map(MessagingInterface::getMessagingInterfaceDescription)
                .orElseThrow(() -> new GenDriverException("Missing messaging interface description in EI-XML"));

        messagingClient = MessagingClientFactory.createClient(ifDesc);
    }

    @Override
    protected Optional<MessagingFunctionalProfile> findProfile(String profileName) {
        return getMessagingInterface().getFunctionalProfileList().getFunctionalProfileListElement().stream()
                .filter(functionalProfile -> functionalProfile.getFunctionalProfile().getFunctionalProfileName().equals(profileName))
                .findFirst();
    }

    @Override
    protected Optional<MessagingDataPoint> findDataPointForProfile(MessagingFunctionalProfile functionalProfile, String datapointName) {
        return functionalProfile.getDataPointList().getDataPointListElement().stream()
                .filter(dataPoint -> dataPoint.getDataPoint().getDataPointName().equals(datapointName))
                .findFirst();
    }

    @Override
    public Value getVal(String profileName, String dataPointName) throws GenDriverException {
        return getVal(profileName, dataPointName, SYNC_READ_TIMEOUT_MSEC);
    }

    @Override
    public Value getVal(String profileName, String dataPointName, long timeoutMs)
            throws GenDriverException {

        MessagingDataPoint dataPoint = findDatapoint(profileName, dataPointName);

        checkReadWritePermission(dataPoint, RwpDirections.READ);

        Optional<OutMessage> outReadCmdTopicOpt = Optional.ofNullable(dataPoint.getMessagingDataPointConfiguration())
                .map(MessagingDataPointConfiguration::getReadCmdMessage);

        String inMessageTopic = Optional.ofNullable(dataPoint.getMessagingDataPointConfiguration())
                .map(MessagingDataPointConfiguration::getInMessage)
                .map(InMessage::getTopic).orElseThrow(() -> new IllegalArgumentException("R and RW datapoints need an inMessageTopic in EI-XML"));

        MessageFilter inMessageFilter = Optional.ofNullable(dataPoint.getMessagingDataPointConfiguration())
                .map(MessagingDataPointConfiguration::getInMessage)
                .map(InMessage::getFilter).orElse(null);

        if (outReadCmdTopicOpt.isPresent()) {
            // Read value from device
            OutMessage outMessage = outReadCmdTopicOpt.get();
            return getValueFromDevice(timeoutMs, dataPoint, outMessage.getTopic(), outMessage.getTemplate(), inMessageTopic, inMessageFilter);
        } else {
            // Read value from cache
            MessageCacheRecord cacheRecord =  messageCache.get(MessageCacheKey.of(inMessageTopic, inMessageFilter));
            return Optional.ofNullable(cacheRecord)
                    .orElseThrow(() -> new GenDriverException(
                            String.format("No value available for functionalProfile=%s, dataPoint=%s. Try calling subscribe()", profileName, dataPointName)))
                    .getValue();
        }
    }

    private Value getValueFromDevice(long timeoutMs, MessagingDataPoint dataPoint, String outMessageTopic, String outMessageTemplate, String inMessageTopic, MessageFilter messageFilter) throws GenDriverException {

        Either<Throwable, Message> result = messagingClient.readSync(
                outMessageTopic,
                Message.of(outMessageTemplate),
                inMessageTopic,
                messageFilter,
                timeoutMs
        );

        // Wait for the response message
        if (result.isRight()) {
            String response = result
                    .get() // returns Message
                    .getPayload();

            Optional<ResponseQuery> queryOpt = Optional.ofNullable(dataPoint.getMessagingDataPointConfiguration())
                    .map(MessagingDataPointConfiguration::getInMessage)
                    .map(InMessage::getResponseQuery);

            if (queryOpt.isPresent()) {
                ResponseQuery responseQuery = queryOpt.get();
                if (responseQuery.isSetQueryType() && ResponseQueryType.JMES_PATH_EXPRESSION==responseQuery.getQueryType()) {
                    return JsonHelper.parseJsonResponse(responseQuery.getQuery(), response);
                } else if (responseQuery.isSetQueryType() && ResponseQueryType.JMES_PATH_MAPPING==responseQuery.getQueryType()) {
                    return JsonHelper.mapJsonResponse(responseQuery.getJmesPathMappings(), response);
                } else if (responseQuery.isSetQueryType()) {
                    throw new GenDriverException("Response query type " + responseQuery.getQueryType().getName() + " not supported yet");
                }
            }
            return StringValue.of(response);
        } else {
            throw new GenDriverException(result.getLeft());
        }
    }

    @Override
    public void setVal(String profileName, String dataPointName, Value value)
            throws GenDriverException {

        MessagingDataPoint dataPoint = findDatapoint(profileName, dataPointName);
        checkReadWritePermission(dataPoint, RwpDirections.WRITE);

        String outMessageTopic = Optional.ofNullable(dataPoint.getMessagingDataPointConfiguration())
                .map(MessagingDataPointConfiguration::getWriteCmdMessage)
                .map(OutMessage::getTopic)
                .orElseThrow(()-> new IllegalArgumentException("W and RW data-points need an outMessageTopic to send the write command within EI-XML"));

        String outMessageTemplate = Optional.ofNullable(dataPoint.getMessagingDataPointConfiguration())
                .map(MessagingDataPointConfiguration::getWriteCmdMessage)
                .map(OutMessage::getTemplate)
                .orElseThrow(() -> new IllegalArgumentException("W and RW data-points need an outMessageTemplate to send the read command within EI-XML"));

        // noinspection RegExpRedundantEscape
        outMessageTemplate = outMessageTemplate.replace("[[value]]", value.getString());

        messagingClient.sendSync(outMessageTopic, Message.of(outMessageTemplate));
    }

    @Override
    public void subscribe(String profileName, String dataPointName, Consumer<Either<Throwable, Value>> callbackFunction) throws GenDriverException {

        MessagingDataPoint dataPoint = findDatapoint(profileName, dataPointName);
        checkReadWritePermission(dataPoint, RwpDirections.READ);

        String inMessageTopic = Optional.ofNullable(dataPoint.getMessagingDataPointConfiguration())
                .map(MessagingDataPointConfiguration::getInMessage)
                .map(InMessage::getTopic).orElseThrow(() -> new IllegalArgumentException("R and RW datapoints need an inMessageTopic in EI-XMK."));

        MessageFilter messageFilter = Optional.ofNullable(dataPoint.getMessagingDataPointConfiguration())
                .map(MessagingDataPointConfiguration::getInMessage)
                .map(InMessage::getFilter).orElse(null);

        messagingClient.subscribe(inMessageTopic, messageFilter, msgReceiveResult ->
                transformIncomingMessage(dataPoint, inMessageTopic,  messageFilter, msgReceiveResult, callbackFunction));
    }

    private void transformIncomingMessage(
            MessagingDataPoint dataPoint,
            String inMessageTopic,
            MessageFilter messageFilter,
            Either<Throwable, Message> msgReceiveResult,
            Consumer<Either<Throwable, Value>> callbackFunction) {

        if (msgReceiveResult.isLeft()) {
            callbackFunction.accept(Either.left(msgReceiveResult.getLeft()));
        }

        String response = Optional.ofNullable(msgReceiveResult.get().getPayload()).orElse("");

        Optional<ResponseQuery> queryOpt = Optional.ofNullable(dataPoint.getMessagingDataPointConfiguration())
                .map(MessagingDataPointConfiguration::getInMessage)
                .map(InMessage::getResponseQuery);

        try {
            Value value;
            if (queryOpt.isPresent()) {
                switch (queryOpt.get().getQueryType()) {
                    case JMES_PATH_EXPRESSION:
                        value = JsonHelper.parseJsonResponse(queryOpt.get().getQuery(), response);
                        break;
                    case JMES_PATH_MAPPING:
                        value = JsonHelper.mapJsonResponse(queryOpt.get().getJmesPathMappings(), response);
                        break;
                    default:
                        throw new GenDriverException("Response query type " + queryOpt.get().getQueryType().getName() + " not supported yet");
                }
            } else {
                value = StringValue.of(response);
            }
            LOG.debug("Received subscribed message on topic={}, filter={}, payload={}",
                    inMessageTopic, queryOpt.isPresent() ? queryOpt.get().getQuery() : "none", response);

            messageCache.put(MessageCacheKey.of(inMessageTopic, messageFilter), MessageCacheRecord.of(value));
            callbackFunction.accept(Either.right(value));
        } catch (Exception e) {
            callbackFunction.accept(Either.left(e));
        }
    }

    @Override
    public void unsubscribe(String profileName, String dataPointName) throws GenDriverException {

        MessagingDataPoint dataPoint = findDatapoint(profileName, dataPointName);

        String inMessageTopic = Optional.ofNullable(dataPoint.getMessagingDataPointConfiguration())
                .map(MessagingDataPointConfiguration::getInMessage)
                .map(InMessage::getTopic).orElseThrow(() -> new IllegalArgumentException("R and RW datapoints need an inMessageTopic in EI-XMK."));

        MessageFilter messageFilter = Optional.ofNullable(dataPoint.getMessagingDataPointConfiguration())
                .map(MessagingDataPointConfiguration::getInMessage)
                .map(InMessage::getFilter).orElse(null);

        messageCache.remove(MessageCacheKey.of(inMessageTopic, messageFilter));
        if (countCacheRecords4Topic(inMessageTopic) == 0) {
            // no subscription is interested in this topic anymore.
            messagingClient.unsubscribe(inMessageTopic);
        }
    }

    @Override
    public void close() throws IOException {
        messagingClient.close();
    }

    private MessagingInterface getMessagingInterface() {
        return Optional.ofNullable(device.getInterfaceList().getMessagingInterface()).orElseThrow(() -> new IllegalArgumentException("No messaging interface defined in EI-XML"));
    }

    private int countCacheRecords4Topic(String topic) {
        AtomicInteger count = new AtomicInteger(0);
        messageCache.keySet().forEach(messageCacheKey -> {
            if (messageCacheKey.getTopic().equals(topic)){
                count.incrementAndGet();
            }
        });
        return count.get();
    }
}
