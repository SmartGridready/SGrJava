package com.smartgridready.communicator.messaging.impl;

import com.smartgridready.communicator.common.api.values.Value;

import java.time.Instant;

public class MessageCacheRecord {
    private final Value value;
    private final Instant lastAccess;

    private MessageCacheRecord(Value value, Instant lastAccess) {
        this.value = value;
        this.lastAccess = lastAccess;
    }

    public static MessageCacheRecord of(Value value) {
        return new MessageCacheRecord(value, Instant.now());
    }

    public Value getValue() {
        return value;
    }

    @SuppressWarnings("unused")
    public Instant getLastAccess() {
        return lastAccess;
    }
}
