package com.smartgridready.communicator.messaging.mapper;

import com.smartgridready.ns.v0.MessagingPlatformType;

import java.util.EnumMap;
import java.util.Map;

/**
 * Maps platform types of specification to driver types.
 */
public class PlatformTypeMapper {

    private PlatformTypeMapper(){/*utility class*/}

    private static final Map<MessagingPlatformType,
            com.smartgridready.driver.api.messaging.model.MessagingPlatformType> MAPPING =
            new EnumMap<>(MessagingPlatformType.class);

    static {
        MAPPING.put(MessagingPlatformType.MQTT_5, com.smartgridready.driver.api.messaging.model.MessagingPlatformType.MQTT5);
        MAPPING.put(MessagingPlatformType.KAFKA, com.smartgridready.driver.api.messaging.model.MessagingPlatformType.KAFKA);
    }

    /**
     * Maps platform type of specification to driver type.
     * @param source the specification type
     * @return a driver platform type
     */
    public static com.smartgridready.driver.api.messaging.model.MessagingPlatformType mapToDriverApi(MessagingPlatformType source) {
        if (source == null) {
            return null;
        }

        return MAPPING.getOrDefault(source, null);
    }
}
