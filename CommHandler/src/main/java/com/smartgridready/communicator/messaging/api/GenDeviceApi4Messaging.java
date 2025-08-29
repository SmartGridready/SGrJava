package com.smartgridready.communicator.messaging.api;

import com.smartgridready.communicator.common.api.GenDeviceApi;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.driver.api.common.GenDriverException;

import java.io.Closeable;
import java.util.Properties;

/**
 * Defines the interface of a message-based device communication interface.
 */
public interface GenDeviceApi4Messaging extends GenDeviceApi, Closeable {

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
     * Gets a value from the device by reading
     *
     * @param profileName The functional profile name
     * @param dataPointName The data point name
     * @param parameters The request-specific parameters
     * @param timeoutMs The read timeout in milliseconds
     * @return The value received from the device
     * @throws GenDriverException if an error occurs
     */
    Value getVal(String profileName, String dataPointName, Properties parameters, long timeoutMs) throws GenDriverException;
}
