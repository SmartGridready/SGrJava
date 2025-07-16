package com.smartgridready.communicator.async.callable;

import com.smartgridready.communicator.async.process.ExecStatus;

import java.time.Instant;

public class AsyncResult<T> {
    private String profileName;
    private String dataPointName;
    private Throwable throwable;
    private T value;
    private Instant requestTime;
    private Instant responseTime;
    private ExecStatus execStatus = ExecStatus.IDLE;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getDataPointName() {
        return dataPointName;
    }

    public void setDataPointName(String dataPointName) {
        this.dataPointName = dataPointName;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Instant getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Instant requestTime) {
        this.requestTime = requestTime;
    }

    public Instant getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Instant responseTime) {
        this.responseTime = responseTime;
    }

    public ExecStatus getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(ExecStatus execStatus) {
        this.execStatus = execStatus;
    }

    @Override
    public String toString() {
        return String.format("%s - %s = %s : status=%s - error=%s, requestTime=%s, responseTime=%s",
                profileName, dataPointName, value, execStatus.name(), throwable, requestTime, responseTime);

    }
}
