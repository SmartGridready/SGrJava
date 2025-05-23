package com.smartgridready.driver.api.messaging.model;

/**
 * Represents a message conveyed by messaging clients.
 */
@SuppressWarnings("unused")
public class Message {

    private final String key;
    private final String payload;

    private Message(String payload) {
        this.key = null;
        this.payload = payload;
    }

    /**
     * Constructor using an optional message key and the message payload.
     * @param key The optional message key
     * @param payload The message payload
     */
    public Message(String key, String payload) {
        this.key = key;
        this.payload = payload;
    }

    /**
     * Gets the message key.
     * @return The optional message key, may return {@code null}
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the message payload.
     * @return The message payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Factory method for a message that has a payload only.
     * @param payload The payload
     * @return A new instance of {@link Message}
     */
    public static Message of(String payload) {
        return new Message(payload);
    }

    /**
     * Factory method for a message with payload and an additional message key.
     * @param key The optional message key
     * @param payload The message payload
     * @return A new instance of {@link Message}
     */
    public static Message of(String key, String payload) {
        return new Message(key, payload);
    }
}
