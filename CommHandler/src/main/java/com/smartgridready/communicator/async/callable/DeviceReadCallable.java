package com.smartgridready.communicator.async.callable;

import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverModbusException;
import com.smartgridready.driver.api.modbus.GenDriverSocketException;
import com.smartgridready.communicator.rest.exception.RestApiAuthenticationException;
import com.smartgridready.communicator.rest.exception.RestApiResponseParseException;
import com.smartgridready.communicator.rest.exception.RestApiServiceCallException;

import java.io.IOException;

/**
 * Callable that wraps a device read command
 * @param <T> Type of the value returned by the device read command.
 */
public class DeviceReadCallable<T> extends BaseCallable<T> {
    private final ReadFunction<T> readFunction;

    public DeviceReadCallable(ReadFunction<T> readFunction, String profileName, String datapointName) {
        super(profileName, datapointName);
        this.readFunction = readFunction;
    }
    @Override
    protected void doFunctionCall() throws
            GenDriverException,
            RestApiResponseParseException,
            GenDriverModbusException,
            RestApiServiceCallException,
            RestApiAuthenticationException,
            GenDriverSocketException,
            IOException {
            result.setValue(readFunction.apply(profileName, datapointName));
    }
    @Override
    public String toString() {
        return String.format("Device READ: Profile=%s Datapoint=%s", profileName, datapointName);
    }
}
