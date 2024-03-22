package communicator.messaging.client;

import com.smartgridready.ns.v0.JMESPathFilterType;
import com.smartgridready.ns.v0.MessageFilter;
import communicator.common.helper.JsonHelper;
import communicator.common.runtime.GenDriverException;
import communicator.messaging.api.Message;
import io.vavr.control.Either;

import javax.naming.OperationNotSupportedException;
import java.io.Closeable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface MessagingClient extends Closeable {

    /**
     * This method publishes a message to the given topic. The method is blocking
     * until the message is acknowledged by the message broker.
     * @param topic The topic to send the message to.
     * @param message The message to send
     */
    void sendSync(String topic, Message message);

    /**
     * Subscribes to a topic and returns a completable future that provides
     * the next message from the given topic.
     * Used to receive the response message of a read-value message/command.
     *
     * @param inMessageTopic The topic to read the value from.
     * @return Either the message received or a Throwable if an error occurred.
     */
    Either<Throwable, Message> readSync(
            String readCmdMessageTopic,
            Message readCmdMessage,
            String inMessageTopic,
            MessageFilter messageFilter,
            long timeoutMs);

    /**
     * Sends a message asynchronously to a given topic. The method does not
     * block and returns immediately without checking the ACK for the
     * message to be sent. However, you could await the ACK when waiting for
     * the completable future that is returned.
     *
     * @param topic The topic to send the message to
     * @param message The message
     * @return A completable future to (optionally) wait for the broker ACK that
     *         the message has been sent.
     */
    CompletableFuture<Either<Throwable, Void>> sendAsynch(String topic, Message message);

    /**
     * Subscribes to a topic for receiving a stream of messages.
     *
     * @param topic The topic to subscribe to
     * @param callback The callback method that handles the incoming messages.
     */
    void subscribe(String topic, MessageFilter messageFilter, Consumer<Either<Throwable, Message>> callback) throws GenDriverException;

    /**
     * @param topic The topic to unsubscribe from
     */
    void unsubscribe(String topic) throws GenDriverException;

    /**
     * Checks if a message payload filter is present and returns true if no filter is present
     * or the message matches the filter criteria.
     *
     * @param payload The received message payload
     * @param messageFilter Payload filter for messages to be processed.
     * @return {@code true} if the message payload matches the filter.
     */
    static boolean isMessagePayloadFilterMatch(String payload, MessageFilter messageFilter) {

        if (messageFilter == null) {
            return true;    // always match
        }
        if (payload == null) {
            return false;    // no match
        }

        String regex = ".";
        if (messageFilter.getJmespathFilter() != null) {
            try {
                JMESPathFilterType filter = messageFilter.getJmespathFilter();
                regex = filter.getMatchesRegex();
                payload = JsonHelper.parseJsonResponse(filter.getQuery(), payload).getString();
            } catch (GenDriverException e) {
                return false; // no match
            }
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(payload);
        return matcher.find();
    }

    static void validateMessagePayloadFilter(MessageFilter messageFilter) throws OperationNotSupportedException {

        if (messageFilter == null) {
            return; // null means no-filter, that's fine
        }
        if (messageFilter.getRegexFilter() != null) {
            throw new OperationNotSupportedException("Regex message filter not supported yet.");
        }
        if (messageFilter.getXpapathFilter() != null) {
            throw new OperationNotSupportedException("Xpath message filter not supported yet.");
        }
    }
}
