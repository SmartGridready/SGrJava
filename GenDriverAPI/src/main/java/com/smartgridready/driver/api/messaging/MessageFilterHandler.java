package com.smartgridready.driver.api.messaging;

import javax.naming.OperationNotSupportedException;

/**
 * Defines the interface of a message filter handler. Optionally sent by communicator.
 */
public interface MessageFilterHandler {
    
    /**
     * Matches the filter against the message payload.
     * @param payload the message payload
     * @return true if match, false otherwise
     */
    public boolean isFilterMatch(String payload);

    /**
     * Checks if the filter is valid.
     * @throws OperationNotSupportedException if the filter is invalid
     */
    public void validate() throws OperationNotSupportedException;
}
