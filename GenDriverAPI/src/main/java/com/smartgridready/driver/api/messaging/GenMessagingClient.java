package com.smartgridready.driver.api.messaging;

import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.messaging.model.Message;
import io.vavr.control.Either;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Defines the interface of a messaging client.
 */
@SuppressWarnings("unused")
public interface GenMessagingClient extends Closeable {

    /**
     * This method publishes a message to the given topic. The method is blocking
     * until the message is acknowledged by the message broker.
     * @param topic The topic to send the message to
     * @param message The message to send
     */
    void sendSync(String topic, Message message);

    /**
     * Subscribes to a topic and returns a completable future that provides
     * the next message from the given topic.
     * Used to receive the response message of a read-value message/command.
     * @param readCmdMessageTopic The topic to issue a read command that triggers a response message
     * @param readCmdMessage The read command message that triggers the response message
     * @param inMessageTopic The topic that receives the response message
     * @param messageFilterHandler An optional filter that filters messages received on the {@code inMessageTopic}
     * @param timeoutMs A message timeout, in ms
     * @return Either the message received or a Throwable if an error occurred.
     */
    Either<Throwable, Message> readSync(
            String readCmdMessageTopic,
            Message readCmdMessage,
            String inMessageTopic,
            MessageFilterHandler messageFilterHandler,
            long timeoutMs);

    /**
     * Sends a message asynchronously to a given topic. The method does not
     * block and returns immediately without checking the ACK for the
     * message to be sent. However, you could await the ACK when waiting for
     * the completable future that is returned.
     * @param topic The topic to send the message to
     * @param message The message
     * @return A completable future to (optionally) wait for the broker ACK that
     *         the message has been sent.
     */
    CompletableFuture<Either<Throwable, Void>> sendAsynch(String topic, Message message);

    /**
     * Subscribes to a topic for receiving a stream of messages.
     * @param topic The topic to subscribe to
     * @param messageFilterHandler An optional filter to filter messages received on the {@code topic}
     * @param callback The callback method that handles the incoming messages
     * @throws GenDriverException when an error occurred
     */
    void subscribe(String topic, MessageFilterHandler messageFilterHandler, Consumer<Either<Throwable, Message>> callback) throws GenDriverException;

    /**
     * Unsubscribes message handlers from a topic.
     * @param topic The topic to unsubscribe from
     * @throws GenDriverException when an error occurred
     */
    void unsubscribe(String topic) throws GenDriverException;
}
