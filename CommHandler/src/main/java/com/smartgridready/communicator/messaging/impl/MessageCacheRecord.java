package com.smartgridready.communicator.messaging.impl;

import com.smartgridready.communicator.common.api.values.Value;

import java.time.Instant;

/**
 * Implements a message cache record.
 */
public class MessageCacheRecord {

    private final Value value;
    private final Instant lastAccess;

    private MessageCacheRecord(Value value, Instant lastAccess) {
        this.value = value;
        this.lastAccess = lastAccess;
    }

    /**
     * Creates a new instance from a value.
     * Sets the time stamp to the current date/time.
     * @param value the value
     * @return a message cache record.
     */
    public static MessageCacheRecord of(Value value) {
        return new MessageCacheRecord(value, Instant.now());
    }

    /**
     * Gets the cached value.
     * @return a value
     */
    public Value getValue() {
        return value;
    }

    /**
     * Gets the access timestamp.
     * @return an instant
     */
    @SuppressWarnings("unused")
    public Instant getLastAccess() {
        return lastAccess;
    }
}
