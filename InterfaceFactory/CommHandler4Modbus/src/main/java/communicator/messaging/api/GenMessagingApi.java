package communicator.messaging.api;

import communicator.common.api.GenDeviceApi;
import communicator.common.api.Value;
import communicator.common.runtime.GenDriverException;
import io.vavr.control.Either;

import java.io.Closeable;
import java.util.function.Consumer;

public interface GenMessagingApi extends GenDeviceApi, Closeable {

    /**
     * Gets a value from the device by reading
     *
     * @param profileName The functional profile name
     * @param dataPointName The data point name
     * @param timeoutMs The read timeout in milliseconds
     * @return The value received from the device
     * @throws GenDriverException if an error occurs
     */
    Value getVal(String profileName, String dataPointName, long timeoutMs) throws GenDriverException;

    /**
     * Subscribes to messages that are related to the given datapoint
     *
     * @param profileName The functional profile name
     * @param dataPointName The data point name
     * @param callbackFunction A callback function that provides the received value
     * @throws GenDriverException if an error occurs
     */
    void subscribe(String profileName, String dataPointName, Consumer<Either<Throwable, Value>> callbackFunction) throws GenDriverException;

    /**
     * Unsubscribes from messages that are related to a given datapoint
     *
     * @param profileName The functional profile name
     * @param dataPointName The datapoint name
     * @throws GenDriverException if an error occurs
     */
    void unsubscribe(String profileName, String dataPointName) throws GenDriverException;
}
