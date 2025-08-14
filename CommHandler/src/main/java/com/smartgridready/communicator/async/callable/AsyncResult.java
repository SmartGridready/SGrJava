package com.smartgridready.communicator.async.callable;

import com.smartgridready.communicator.async.process.ExecStatus;

import java.time.Instant;

/**
 * Implements an asynchronous process result.
 * @param <T> The type of result.
 */
public class AsyncResult<T> {

    private String profileName;
    private String dataPointName;
    private Throwable throwable;
    private T value;
    private Instant requestTime;
    private Instant responseTime;
    private ExecStatus execStatus;

    /**
     * Constructs a new instance.
     */
    public AsyncResult() {
        execStatus = ExecStatus.IDLE;
    }

    /**
     * Gets the functional profile name.
     * @return a string
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * Sets the functional profile name.
     * @param profileName he functional profile name
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * Gets the data point name.
     * @return a string
     */
    public String getDataPointName() {
        return dataPointName;
    }

    /**
     * Sets the data point name.
     * @param dataPointName the data point name
     */
    public void setDataPointName(String dataPointName) {
        this.dataPointName = dataPointName;
    }

    /**
     * Gets the exception that was thrown.
     * @return an instance of {@link Throwable}
     */
    public Throwable getThrowable() {
        return throwable;
    }

    /**
     * Sets the exception that was thrown.
     * @param throwable the exception
     */
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    /**
     * Gets the result value.
     * @return an instance of the value type
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the result value.
     * @param value the value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Gets the time stamp of sending the request.
     * @return an instance of {@link Instant}
     */
    public Instant getRequestTime() {
        return requestTime;
    }

    /**
     * Sets the time stamp of sending the request.
     * @param requestTime the time stamp
     */
    public void setRequestTime(Instant requestTime) {
        this.requestTime = requestTime;
    }

    /**
     * Gets the time stamp of getting the response.
     * @return an instance of {@link Instant}
     */
    public Instant getResponseTime() {
        return responseTime;
    }

    /**
     * Sets the time stamp of getting the response.
     * @param responseTime the time stamp
     */
    public void setResponseTime(Instant responseTime) {
        this.responseTime = responseTime;
    }

    /**
     * Gets the process status.
     * @return an instance of {@link ExecStatus}
     */
    public ExecStatus getExecStatus() {
        return execStatus;
    }

    /**
     * Sets the process status.
     * @param execStatus the process status
     */
    public void setExecStatus(ExecStatus execStatus) {
        this.execStatus = execStatus;
    }

    @Override
    public String toString() {
        return String.format("%s - %s = %s : status=%s - error=%s, requestTime=%s, responseTime=%s",
                profileName, dataPointName, value, execStatus.name(), throwable, requestTime, responseTime);

    }
}
