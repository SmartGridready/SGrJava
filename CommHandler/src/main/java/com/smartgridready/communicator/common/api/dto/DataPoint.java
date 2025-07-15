package com.smartgridready.communicator.common.api.dto;

import com.smartgridready.communicator.common.api.GenDeviceApi;
import com.smartgridready.communicator.common.api.values.DataTypeInfo;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.messaging.api.GenDeviceApi4Messaging;
import com.smartgridready.communicator.rest.api.GenDeviceApi4Rest;
import com.smartgridready.communicator.rest.exception.RestApiAuthenticationException;
import com.smartgridready.communicator.rest.exception.RestApiResponseParseException;
import com.smartgridready.communicator.rest.exception.RestApiServiceCallException;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverModbusException;
import com.smartgridready.driver.api.modbus.GenDriverSocketException;
import com.smartgridready.ns.v0.DataDirectionProduct;
import com.smartgridready.ns.v0.Units;
import io.vavr.control.Either;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.Properties;

public class DataPoint {

    private final String name;
    private final String functionalProfileName;
    private final DataTypeInfo dataType;
    private final Units unit;
    private final DataDirectionProduct permissions;
    private final Double minimumValue;
    private final Double maximumValue;
    private final Integer arrayLen;
    private final List<DynamicRequestParameter> dynamicRequestParameters;
    private final List<GenericAttribute> genericAttributes;

    protected final GenDeviceApi genDeviceApi;

    @SuppressWarnings("java:S107")
    public DataPoint(String name,
                     String functionalProfileName,
                     DataTypeInfo dataType,
                     Units unit,
                     DataDirectionProduct permissions,
                     Double minimumValue,
                     Double maximumValue,
                     Integer arrayLen,
                     List<GenericAttribute> genericAttributes,
                     List<DynamicRequestParameter> dynamicRequestParameters,
                     GenDeviceApi genDeviceApi) {
        this.name = name;
        this.functionalProfileName = functionalProfileName;
        this.dataType = dataType;
        this.unit = unit;
        this.permissions = permissions;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.arrayLen = arrayLen;
        this.genericAttributes = genericAttributes;
        this.dynamicRequestParameters = dynamicRequestParameters;
        this.genDeviceApi = genDeviceApi;
    }

    /**
     * @return The data point name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The functional profile name
     */
    public String getFunctionalProfileName() {return functionalProfileName; }

    /**
     * @return The data type name and range
     */
    public DataTypeInfo getDataType() {
        return dataType;
    }

    /**
     * @return The units of the data point value
     */
    public Units getUnit() {
        return unit;
    }

    /**
     * @return The RW permissions
     */
    public DataDirectionProduct getPermissions() {
        return permissions;
    }

    /**
     * @return The minimum allowed value for {@code setVal()}
     */
    public Double getMinimumValue() {
        return minimumValue;
    }

    /**
     * @return The maximum allowed value for {@code setVal()}
     */
    public Double getMaximumValue() {
        return maximumValue;
    }

    /**
     * @return The array length if the data point represents an array of values.
     */
    public Integer getArrayLen() {
        return arrayLen;
    }

    /**
     * @return A list of generic attributes for this data point.
     */
    public List<GenericAttribute> getGenericAttributes() {
        return genericAttributes;
    }

    /**
     * @return A list of the request parameters that need to be provided as {@link Properties}
     *         when calling the method
     *         {@link com.smartgridready.communicator.common.api.GenDeviceApi#getVal(String, String, Properties)}
     */
    public List<DynamicRequestParameter> getDynamicRequestParameters() {
        return dynamicRequestParameters;
    }

    /**
     * Read the value from this data point
     * @return The value read.
     * @throws GenDriverException On a generic error
     * @throws RestApiResponseParseException If the web service response could not be parsed
     * @throws GenDriverModbusException If the modbus command could not be processed
     * @throws RestApiServiceCallException If web service call failed
     * @throws GenDriverSocketException If the COM port socket unexpectedly closed
     * @throws IOException On generic IO operation errors
     */
    public Value getVal() throws GenDriverException, RestApiResponseParseException, GenDriverModbusException, RestApiServiceCallException, GenDriverSocketException, IOException {
        return genDeviceApi.getVal(functionalProfileName, name);
    }

    /**
     * Read the value from this data point, with request-specific parameters
     * @param parameters Key value pairs with request parameters 
     * @return The value read.
     * @throws GenDriverException On a generic error
     * @throws RestApiResponseParseException If the web service response could not be parsed
     * @throws GenDriverModbusException If the modbus command could not be processed
     * @throws RestApiServiceCallException If web service call failed
     * @throws GenDriverSocketException If the COM port socket unexpectedly closed
     * @throws IOException On generic IO operation errors
     */
    public Value getVal(Properties parameters) throws GenDriverException, RestApiResponseParseException, GenDriverModbusException, RestApiServiceCallException, GenDriverSocketException, IOException {
        return genDeviceApi.getVal(functionalProfileName, name, parameters);
    }

    /**
     * Write a value to the data point
     * @param value The value
     * @throws GenDriverException On a generic error
     * @throws RestApiResponseParseException If the web service response could not be parsed
     * @throws GenDriverModbusException If the modbus command could not be processed
     * @throws RestApiServiceCallException If web service call failed
     * @throws GenDriverSocketException If the COM port socket unexpectedly closed
     * @throws IOException On generic IO operation errors
     */
    public void setVal(Value value) throws GenDriverException, RestApiResponseParseException, GenDriverModbusException, RestApiServiceCallException, GenDriverSocketException, IOException {
        genDeviceApi.setVal(functionalProfileName, name, value);
    }

    /**
     * Does the authentication to access the web service if needed.
     * Note: can be removed, as this is already covered by connect()
     * @throws RestApiResponseParseException  If the authentication response could not be parsed
     * @throws RestApiAuthenticationException If the authentication could not be performed
     * @throws RestApiServiceCallException If the REST API service call failed
     * @throws IOException If an IO operation error occurred
     * @deprecated Since version 2.1.0.
     */
    @Deprecated(since = "2.1.0", forRemoval = true)
    public void authenticate() throws RestApiResponseParseException, RestApiAuthenticationException, RestApiServiceCallException, IOException {
        if (genDeviceApi instanceof GenDeviceApi4Rest) {
            ((GenDeviceApi4Rest)genDeviceApi).authenticate();
        } else {
            throw new UnsupportedOperationException("Method authenticate() is supported for REST API devices only.");
        }
    }

    /**
     * Gets a value from the device by reading
     * This operation is supported for messaging devices only
     * @param timeoutMs The read timeout in milliseconds
     * @return The value received from the device
     * @throws GenDriverException if an error occurs
     */
    public Value getVal(long timeoutMs) throws GenDriverException {
        if (genDeviceApi instanceof GenDeviceApi4Messaging) {
            return ((GenDeviceApi4Messaging) genDeviceApi).getVal(functionalProfileName, name, timeoutMs);
        } else {
            throw new UnsupportedOperationException(
                    "Method getVal() with an additional timeout parameter is supported for messaging devices only.");
        }
    }

    /**
     * Gets a value from the device by reading
     * This operation is supported for messaging devices only
     * @param timeoutMs The read timeout in milliseconds
     * @return The value received from the device
     * @throws GenDriverException if an error occurs
     */
    public Value getVal(Properties parameters, long timeoutMs) throws GenDriverException {
        if (genDeviceApi instanceof GenDeviceApi4Messaging) {
            return ((GenDeviceApi4Messaging) genDeviceApi).getVal(functionalProfileName, name, parameters, timeoutMs);
        } else {
            throw new UnsupportedOperationException(
                    "Method getVal() with an additional timeout parameter is supported for messaging devices only.");
        }
    }

    /**
     * Subscribes to messages that are related to the given data point
     * This operation is supported for messaging devices only
     * @param callbackFunction A callback function that provides the received value
     * @throws GenDriverException if an error occurs
     */
    public void subscribe(Consumer<Either<Throwable, Value>> callbackFunction) throws GenDriverException {
        if (genDeviceApi.canSubscribe()) {
            genDeviceApi.subscribe(functionalProfileName, name, callbackFunction);
        } else {
            throw new UnsupportedOperationException("Method subscribe() is supported for messaging devices only.");
        }
    }

    /**
     * Unsubscribes from messages that are related to a given data point
     * This operation is supported for messaging devices only
     * @throws GenDriverException if an error occurs
     */
    public void unsubscribe() throws GenDriverException {
        if (genDeviceApi.canSubscribe()) {
            genDeviceApi.unsubscribe(functionalProfileName, name);
        } else {
            throw new UnsupportedOperationException("Method unsubscribe() is supported for messaging devices only.");
        }
    }

    public boolean canSubscribe() {
        return genDeviceApi.canSubscribe();
    }
}
