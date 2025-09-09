package com.smartgridready.communicator.async.callable;

import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverModbusException;
import com.smartgridready.driver.api.modbus.GenDriverSocketException;
import com.smartgridready.communicator.rest.exception.RestApiAuthenticationException;
import com.smartgridready.communicator.rest.exception.RestApiResponseParseException;
import com.smartgridready.communicator.rest.exception.RestApiServiceCallException;

import java.io.IOException;

/**
 * Defines the interface of a write function.
 * @param <V> The type of value to write.
 */
@FunctionalInterface
public interface WriteFunction<V> {

    /**
     * Executes the function.
     * @param functionalProfile the functional profile name
     * @param dataPoint the data point name
     * @param value the value to set
     * @throws GenDriverException when an unspecific error occurred
     * @throws GenDriverModbusException when a Modbus error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws IOException when a communication error occurred
     * @throws RestApiServiceCallException when the HTTP request failed
     * @throws RestApiAuthenticationException when HTTP authentication failed
     * @throws RestApiResponseParseException when the HTTP response could not be parsed
     */
    void apply(String functionalProfile, String dataPoint, V value) throws
            GenDriverException, GenDriverModbusException, GenDriverSocketException, IOException,
            RestApiServiceCallException, RestApiAuthenticationException, RestApiResponseParseException;
}
