package com.smartgridready.communicator.async.process;

/**
 * Defines execution states of an asynchronous process.
 */
public enum ExecStatus {
    /** Idle. */
    IDLE,
    /** Processing. */
    PROCESSING,
    /** Completed successfully. */
    SUCCESS,
    /** Completed with error. */
    ERROR;

    /**
     * Tells if the process is not yet finished.
     * @return a boolean
     */
    public boolean isNotProcessed() {
        return this.equals(IDLE) || this.equals(PROCESSING);
    }
}
