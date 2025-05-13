package com.smartgridready.driver.api.messaging;

import java.util.Set;

import com.smartgridready.driver.api.messaging.model.MessagingInterfaceDescription;
import com.smartgridready.driver.api.messaging.model.MessagingPlatformType;

/**
 * Defines the interface of a messaging client factory.
 */
@SuppressWarnings("unused")
public interface GenMessagingClientFactory {

    /**
     * Factory method to create a new instance of the messaging client.
     * @param interfaceDescription Describes the messaging interface and it's parameters
     * @return A new instance of {@link GenMessagingClient}
     */
    GenMessagingClient create(MessagingInterfaceDescription interfaceDescription);

    /**
     * Gets the messaging platforms supported by the implementation.
     * @return a set of {@link MessagingPlatformType}
     */
    Set<MessagingPlatformType> getSupportedPlatforms();
}
