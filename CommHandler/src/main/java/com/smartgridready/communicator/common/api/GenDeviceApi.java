package com.smartgridready.communicator.common.api;

import com.smartgridready.communicator.common.api.dto.ConfigurationValue;
import com.smartgridready.communicator.common.api.dto.DataPoint;
import com.smartgridready.communicator.common.api.dto.DataPointValue;
import com.smartgridready.communicator.common.api.dto.DeviceInfo;
import com.smartgridready.communicator.common.api.dto.FunctionalProfile;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverModbusException;
import com.smartgridready.driver.api.modbus.GenDriverSocketException;

import io.vavr.control.Either;

import com.smartgridready.communicator.rest.exception.RestApiResponseParseException;
import com.smartgridready.communicator.rest.exception.RestApiServiceCallException;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.Properties;

public interface GenDeviceApi {

    /**
     * Get a value for a given data point
     * @param profileName The functional profile name
     * @param dataPointName The data point name
     * @return The value read from the device
     * @throws GenDriverException Generic error while reading from the device
     * @throws GenDriverSocketException A connection error occurred
     * @throws GenDriverModbusException The modbus command returned an error code
     * @throws RestApiServiceCallException The REST API service call failed
     * @throws RestApiResponseParseException The REST API response could not be parsed
     * @throws IOException Generic communication exception occurred
     */
    Value getVal(String profileName, String dataPointName) throws
            GenDriverException, GenDriverSocketException, GenDriverModbusException,
            RestApiServiceCallException, RestApiResponseParseException, IOException;

    /**
     * Get a value for a given data point, with request-specific parameters
     * @param profileName The functional profile name
     * @param dataPointName The data point name
     * @param parameters Key value pairs with request parameters
     * @return The value read from the device
     * @throws GenDriverException Generic error while reading from the device
     * @throws GenDriverSocketException A connection error occurred
     * @throws GenDriverModbusException The modbus command returned an error code
     * @throws RestApiServiceCallException The REST API service call failed
     * @throws RestApiResponseParseException The REST API response could not be parsed
     * @throws IOException Generic communication exception occurred
     */
    Value getVal(String profileName, String dataPointName, Properties parameters) throws
            GenDriverException, GenDriverSocketException, GenDriverModbusException,
            RestApiServiceCallException, RestApiResponseParseException, IOException;

    /**
     * Set a value for a given data point
     * @param profileName The functional profile name
     * @param dataPointName The data point name
     * @param value The value to be set
     * @throws GenDriverException Generic error while reading from the device
     * @throws GenDriverSocketException A connection error occurred
     * @throws GenDriverModbusException The modbus command returned an error code
     * @throws RestApiServiceCallException The REST API service call failed
     * @throws RestApiResponseParseException The REST API response could not be parsed
     * @throws IOException Generic communication exception occurred
     */
    void setVal(String profileName, String dataPointName, Value value) throws
            GenDriverException, GenDriverSocketException, GenDriverModbusException,
            RestApiServiceCallException, RestApiResponseParseException, IOException;

    /**
     * Get value of all data points in one go
     * @return The values read from the device, by data point
     * @throws GenDriverException Generic error while reading from the device
     */
    List<DataPointValue> getValues() throws GenDriverException;

    /**
     * Get device information
     * @return The device information
     * @throws GenDriverException Generic error while reading from the device
     */
    DeviceInfo getDeviceInfo() throws GenDriverException;

    /**
     * Get the device interface configuration parameters
     * @return The configuration parameter list
     */
    List<ConfigurationValue> getDeviceConfigurationInfo();

    /**
     * Get all functional profiles
     * @return The functional profiles
     * @throws GenDriverException Generic error while reading from the device
     */
    List<FunctionalProfile> getFunctionalProfiles() throws GenDriverException;

    /**
     * Get a functional profile
     * @param functionalProfileName The name of the functional profile
     * @return The functional profile
     * @throws GenDriverException Generic error while reading from the device
     */
    FunctionalProfile getFunctionalProfile(String functionalProfileName) throws GenDriverException;

    /**
     * Get all data points of a functional profile
     * @param functionalProfileName The name of the functional profile
     * @return The data points
     * @throws GenDriverException Generic error while reading from the device
     */
    List<DataPoint> getDataPoints(String functionalProfileName) throws GenDriverException;

    /**
     * Get a data point
     * @param functionalProfileName The name of the functional profile
     * @param dataPointName The name of the data point
     * @return The data point
     * @throws GenDriverException Generic error while reading from the device
     */
    DataPoint getDataPoint(String functionalProfileName, String dataPointName) throws GenDriverException;

    /**
     * Connect the device.
     * @throws GenDriverException Generic error while reading from the device
     */
    void connect() throws GenDriverException;

    /**
     * Disconnect the device.
     * @throws GenDriverException Generic error while reading from the device
     */
    void disconnect() throws GenDriverException;

    /**
     * Checks if the device is connected.
     * @return true if connected, false otherwise
     */
    boolean isConnected();

    /**
     * Subscribes to messages that are related to the given data point
     * @param profileName The functional profile name
     * @param dataPointName The data point name
     * @param callbackFunction A callback function that provides the received value
     * @throws GenDriverException if an error occurs
     */
    void subscribe(String profileName, String dataPointName, Consumer<Either<Throwable, Value>> callbackFunction) throws GenDriverException;

    /**
     * Unsubscribes from messages that are related to a given data point
     * @param profileName The functional profile name
     * @param dataPointName The data point name
     * @throws GenDriverException if an error occurs
     */
    void unsubscribe(String profileName, String dataPointName) throws GenDriverException;

    /**
     * Checks whether the device can subscribe to data points.
     * @return true if subscribe possible, false otherwise
     */
    boolean canSubscribe();
}
