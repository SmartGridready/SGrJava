package com.smartgridready.driver.api.messaging.model;

import com.smartgridready.driver.api.messaging.model.authentication.MessageBrokerAuthentication;

import java.util.List;

/**
 * Implements a messaging interface description.
 */
@SuppressWarnings("unused")
public class MessagingInterfaceDescription {

    private final MessagingPlatformType messagingPlatformType;

    private final List<MessageBroker> messageBrokerList;

    private final String clientId;

    private final MessageBrokerAuthentication messageBrokerAuthentication;

    /**
     * Construct.
     * @param messagingPlatformType the messaging platform type
     * @param messageBrokerList a list of message brokers to connect to
     * @param messageBrokerAuthentication the message broker authentication configuration
     * @param clientId the messaging client identifier
     */
    public MessagingInterfaceDescription(
            MessagingPlatformType messagingPlatformType,
            List<MessageBroker> messageBrokerList,
            MessageBrokerAuthentication messageBrokerAuthentication,
            String clientId
    ) {
        this.messagingPlatformType = messagingPlatformType;
        this.messageBrokerList = messageBrokerList;
        this.messageBrokerAuthentication = messageBrokerAuthentication;
        this.clientId = clientId;
    }

    /**
     * Gets the messaging platform type.
     * @return a platform type
     */
    public MessagingPlatformType getMessagingPlatformType() {
        return messagingPlatformType;
    }

    /**
     * Gets the list of configured message brokers.
     * @return a list of message broker configurations
     */
    public List<MessageBroker> getMessageBrokerList() {
        return messageBrokerList;
    }

    /**
     * Gets the message broker authentication configuration.
     * @return an authentication configuration
     */
    public MessageBrokerAuthentication getMessageBrokerAuthentication() {
        return messageBrokerAuthentication;
    }

    /**
     * Gets the messaging client identifier.
     * @return a string
     */
    public String getClientId() {
        return clientId;
    }
}
