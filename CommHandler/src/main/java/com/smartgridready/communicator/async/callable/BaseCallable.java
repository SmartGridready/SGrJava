package com.smartgridready.communicator.async.callable;

import com.smartgridready.communicator.async.process.ExecStatus;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverModbusException;
import com.smartgridready.driver.api.modbus.GenDriverSocketException;
import com.smartgridready.communicator.rest.exception.RestApiAuthenticationException;
import com.smartgridready.communicator.rest.exception.RestApiResponseParseException;
import com.smartgridready.communicator.rest.exception.RestApiServiceCallException;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.Callable;

/**
 * Base class for callable methods on SGr devices.
 * @param <T> the result type
 */
public abstract class BaseCallable<T> implements Callable<AsyncResult<T>> {

    /** The functional profile name. */
    protected final String profileName;
    /** The data point name. */
    protected final String dataPointName;
    /** The execution result. */
    protected final AsyncResult<T> result = new AsyncResult<>();

    abstract void doFunctionCall()
            throws GenDriverException, RestApiResponseParseException, GenDriverModbusException,
            RestApiServiceCallException, RestApiAuthenticationException, GenDriverSocketException,
            IOException;

    /**
     * Constructs a new instance.
     * @param profileName the functional profile name
     * @param dataPointName the data point name
     */
    public BaseCallable(String profileName, String dataPointName) {
        this.profileName = profileName;
        this.dataPointName = dataPointName;
        this.result.setExecStatus(ExecStatus.IDLE);
    }

    @Override
    public AsyncResult<T> call() {
        result.setProfileName(profileName);
        result.setDataPointName(dataPointName);
        result.setExecStatus(ExecStatus.PROCESSING);
        try {
            result.setRequestTime(Instant.now());
            doFunctionCall();
            result.setExecStatus(ExecStatus.SUCCESS);
            result.setResponseTime(Instant.now());
        } catch (Throwable t) {
            result.setResponseTime(Instant.now());
            result.setExecStatus(ExecStatus.ERROR);
            result.setThrowable(t);
        }
        return result;
    }

    /**
     * Gets the functional profile name.
     * @return a string
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * Gets the functional profile name.
     * @return a string
     */
    public String getDataPointName() {
        return dataPointName;
    }

    /**
     * Gets the process result.
     * @return an instance of {@link AsyncResult}
     */
    public AsyncResult<T> getResult() {
        return result;
    }
}
