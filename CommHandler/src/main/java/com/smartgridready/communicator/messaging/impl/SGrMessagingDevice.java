package com.smartgridready.communicator.messaging.impl;

import com.smartgridready.communicator.messaging.mapper.MessageFilterMapper;
import com.smartgridready.communicator.messaging.mapper.MessagingInterfaceDescMapper;
import com.smartgridready.driver.api.messaging.model.Message;
import com.smartgridready.driver.api.messaging.GenMessagingClient;
import com.smartgridready.driver.api.messaging.GenMessagingClientFactory;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.InMessage;
import com.smartgridready.ns.v0.InterfaceList;
import com.smartgridready.ns.v0.MessageFilter;
import com.smartgridready.ns.v0.MessagingDataPoint;
import com.smartgridready.ns.v0.MessagingDataPointConfiguration;
import com.smartgridready.ns.v0.MessagingFunctionalProfile;
import com.smartgridready.ns.v0.MessagingInterface;
import com.smartgridready.ns.v0.MessagingInterfaceDescription;
import com.smartgridready.ns.v0.MessagingValueMapping;
import com.smartgridready.ns.v0.OutMessage;
import com.smartgridready.ns.v0.ResponseQuery;
import com.smartgridready.ns.v0.ResponseQueryType;
import com.smartgridready.ns.v0.ValueMapping;
import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.common.helper.JsonHelper;
import com.smartgridready.communicator.common.impl.SGrDeviceBase;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.communicator.messaging.api.GenDeviceApi4Messaging;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class SGrMessagingDevice extends SGrDeviceBase<
        DeviceFrame,
        MessagingFunctionalProfile,
        MessagingDataPoint> implements GenDeviceApi4Messaging {

    private static final Logger LOG = LoggerFactory.getLogger(SGrMessagingDevice.class);

    private static final long SYNC_READ_TIMEOUT_MSEC = 60000;

    private static final String NOT_CONNECTED = "not connected";

    private final MessagingInterfaceDescription interfaceDescription;

    private final GenMessagingClientFactory messagingClientFactory;

    private GenMessagingClient messagingClient;

    private final Map<MessageCacheKey, MessageCacheRecord> messageCache = new HashMap<>();

    public SGrMessagingDevice(DeviceFrame deviceDescription,
                              GenMessagingClientFactory messagingClientFactory) throws GenDriverException {
        super(deviceDescription);

        interfaceDescription = Optional.ofNullable(deviceDescription.getInterfaceList())
            .map(InterfaceList::getMessagingInterface)
            .map(MessagingInterface::getMessagingInterfaceDescription)
            .orElseThrow(() -> new GenDriverException("Missing messaging interface description in EI-XML"));

        this.messagingClientFactory = messagingClientFactory;
        messagingClient = null;
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
        if (messagingClient == null) {
            throw new GenDriverException(NOT_CONNECTED);
        }

        Either<Throwable, Message> result = messagingClient.readSync(
                outMessageTopic,
                Message.of(outMessageTemplate),
                inMessageTopic,
                MessageFilterMapper.INSTANCE.mapToDriverApi(messageFilter),
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
                if (responseQuery.getQueryType() != null && ResponseQueryType.JMES_PATH_EXPRESSION == responseQuery.getQueryType()) {
                    return JsonHelper.parseJsonResponse(responseQuery.getQuery(), response);
                } else if (responseQuery.getQueryType() != null && ResponseQueryType.JMES_PATH_MAPPING == responseQuery.getQueryType()) {
                    return JsonHelper.mapJsonResponse(responseQuery.getJmesPathMappings(), response);
                } else if (responseQuery.getQueryType() != null) {
                    throw new GenDriverException("Response query type " + responseQuery.getQueryType().name() + " not supported yet");
                }
            }

            // mapping device -> generic (only for plain string values)
            return getMappedGenericValue(dataPoint.getMessagingDataPointConfiguration(), response);
        } else {
            throw new GenDriverException(result.getLeft());
        }
    }

    @Override
    public void setVal(String profileName, String dataPointName, Value value)
            throws GenDriverException {
        if (messagingClient == null) {
            throw new GenDriverException(NOT_CONNECTED);
        }

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

        // mapping generic -> device
        String outValue = getMappedDeviceValue(dataPoint.getMessagingDataPointConfiguration(), value);

        // noinspection RegExpRedundantEscape
        outMessageTemplate = outMessageTemplate.replace("{{value}}", outValue);

        messagingClient.sendSync(outMessageTopic, Message.of(outMessageTemplate));
    }

    @Override
    public void subscribe(String profileName, String dataPointName, Consumer<Either<Throwable, Value>> callbackFunction) throws GenDriverException {
        if (messagingClient == null) {
            throw new GenDriverException(NOT_CONNECTED);
        }

        MessagingDataPoint dataPoint = findDatapoint(profileName, dataPointName);
        checkReadWritePermission(dataPoint, RwpDirections.READ);

        String inMessageTopic = Optional.ofNullable(dataPoint.getMessagingDataPointConfiguration())
                .map(MessagingDataPointConfiguration::getInMessage)
                .map(InMessage::getTopic).orElseThrow(() -> new IllegalArgumentException("R and RW datapoints need an inMessageTopic in EI-XMK."));

        MessageFilter messageFilter = Optional.ofNullable(dataPoint.getMessagingDataPointConfiguration())
                .map(MessagingDataPointConfiguration::getInMessage)
                .map(InMessage::getFilter).orElse(null);

        messagingClient.subscribe(inMessageTopic, MessageFilterMapper.INSTANCE.mapToDriverApi(messageFilter), msgReceiveResult ->
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
                        throw new GenDriverException("Response query type " + queryOpt.get().getQueryType().name() + " not supported yet");
                }
            } else {
                // mapping device -> generic (only for plain string values)
                value = getMappedGenericValue(dataPoint.getMessagingDataPointConfiguration(), response);
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
        if (messagingClient == null) {
            throw new GenDriverException(NOT_CONNECTED);
        }

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
    public synchronized void close() throws IOException {
        if (messagingClient != null) {
            messagingClient.close();
            messagingClient = null;
        }
    }

    @Override
	public synchronized void connect() throws GenDriverException {
        if (messagingClient == null) {
            messagingClient = messagingClientFactory.create(
                    MessagingInterfaceDescMapper.INSTANCE.mapToDriverApi(interfaceDescription));
        }
	}

	@Override
	public void disconnect() throws GenDriverException {
        try {
		    close();
        } catch (IOException e) {
            throw new GenDriverException("Error closing messaging client", e);
        } finally {
            messagingClient = null;
        }
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

    private static Value getMappedGenericValue(MessagingDataPointConfiguration dataPointConfiguration, String value) {
        String mappedValue = value;

        List<ValueMapping> valueMappings = Optional.ofNullable(dataPointConfiguration)
            .map(MessagingDataPointConfiguration::getInMessage)
            .map(InMessage::getValueMapping)
            .map(MessagingValueMapping::getMapping).orElse(Collections.emptyList());
        for (ValueMapping mapping: valueMappings) {
            if (mappedValue.equals(mapping.getDeviceValue())) {
                mappedValue = mapping.getGenericValue();
                break;
            }
        }

        return StringValue.of(mappedValue);
    }

    private static String getMappedDeviceValue(MessagingDataPointConfiguration dataPointConfiguration, Value value) {
        String mappedValue = value.getString();

        List<ValueMapping> valueMappings = Optional.ofNullable(dataPointConfiguration)
            .map(MessagingDataPointConfiguration::getWriteCmdMessage)
            .map(OutMessage::getValueMapping)
            .map(MessagingValueMapping::getMapping).orElse(Collections.emptyList());
        for (ValueMapping mapping: valueMappings) {
            if (mappedValue.equals(mapping.getGenericValue())) {
                mappedValue = mapping.getDeviceValue();
                break;
            }
        }

        return mappedValue;
    }
}
