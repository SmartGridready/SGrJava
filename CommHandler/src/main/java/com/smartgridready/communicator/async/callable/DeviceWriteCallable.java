package com.smartgridready.communicator.async.callable;

import communicator.common.runtime.GenDriverException;
import communicator.common.runtime.GenDriverModbusException;
import communicator.common.runtime.GenDriverSocketException;
import com.smartgridready.communicator.rest.exception.RestApiAuthenticationException;
import com.smartgridready.communicator.rest.exception.RestApiResponseParseException;
import com.smartgridready.communicator.rest.exception.RestApiServiceCallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Callable that wraps a device write command.
 * @param <T> The type of the value written to the device.
 */
public class DeviceWriteCallable<T> extends BaseCallable<T> {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceWriteCallable.class);

    private final WriteFunction<T> writeFunction;

    private T writeValue;

    public DeviceWriteCallable(WriteFunction<T> writeFunction, String profileName, String datapointName) {
        super(profileName, datapointName);
        this.writeFunction = writeFunction;
        this.writeValue = null;
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
        if (writeValue != null) {
            writeFunction.apply(profileName, datapointName, writeValue);
        } else {
            LOG.warn("No value provided for DEVICE WRITE. Write was not executed.");
        }
    }

    public void setWriteValue(T writeValue) {
        this.writeValue = writeValue;
    }
    @Override
    public String toString() {
        return String.format("Device WRITE: Profile=%s Datapoint=%s", profileName, datapointName);
    }
}
