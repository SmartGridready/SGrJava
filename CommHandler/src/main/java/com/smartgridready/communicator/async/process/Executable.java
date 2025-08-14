package com.smartgridready.communicator.async.process;

import com.smartgridready.communicator.async.callable.AsyncResult;

/**
 * Defines the interface of an asynchronous process.
 */
public interface Executable {

    /**
     * Gets the execution status.
     * @return an instance of {@link ExecStatus}
     */
    ExecStatus getExecStatus();

    /**
     * Sets an object which should receive notifications of status changes.
     * @param aReceiver the receiving object
     */
    void setFinishedNotificationReceiver(Object aReceiver);

    /**
     * Gets the process result.
     * @return an instance of {@link AsyncResult} with generic type
     */
    AsyncResult<?> getResult();
}
