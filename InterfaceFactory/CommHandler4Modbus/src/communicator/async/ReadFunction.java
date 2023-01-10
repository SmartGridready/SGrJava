package communicator.async;

import communicator.common.runtime.GenDriverException;
import communicator.common.runtime.GenDriverModbusException;
import communicator.common.runtime.GenDriverSocketException;
import communicator.restapi.exception.RestApiAuthenticationException;
import communicator.restapi.exception.RestApiResponseParseException;
import communicator.restapi.exception.RestApiServiceCallException;

import java.io.IOException;

public interface ReadFunction<R> {
    R apply(String functionalProfile, String dataPoint) throws
            GenDriverException, GenDriverModbusException, GenDriverSocketException, IOException,
            RestApiServiceCallException, RestApiAuthenticationException, RestApiResponseParseException;
}
