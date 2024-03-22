package communicator.common.api;

import communicator.common.runtime.GenDriverException;
import communicator.common.runtime.GenDriverModbusException;
import communicator.common.runtime.GenDriverSocketException;
import communicator.rest.exception.RestApiResponseParseException;
import communicator.rest.exception.RestApiServiceCallException;

import java.io.IOException;
import java.util.List;

public interface GenDeviceApi {

    /**
     * Lists the generic attributes of a device
     * @param profileName The name of the functional profile
     * @param dataPointName The data point name
     * @return a list with the generic attributes of the device
     * @throws GenDriverException if an error occurred
     */
    List<GenericAttribute> getGenAttributes(String profileName, String dataPointName) throws GenDriverException;

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
}
