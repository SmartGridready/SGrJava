package com.smartgridready.communicator.messaging.mapper;

import com.smartgridready.ns.v0.MessagingInterfaceDescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Defines the interface of an interface description mapper.
 */
@Mapper(uses = {BrokerListMapper.class})
public interface MessagingInterfaceDescMapper {

    /**
     * The default singleton instance.
     */
    MessagingInterfaceDescMapper INSTANCE = Mappers.getMapper( MessagingInterfaceDescMapper.class );

    /**
     * Maps the interface specification to its driver equivalent.
     * @param source the interface description
     * @return the driver interface description
     */
    @Mapping(target = "messagingPlatformType", expression = "java( PlatformTypeMapper.mapToDriverApi(source.getPlatform()) )")
    com.smartgridready.driver.api.messaging.model.MessagingInterfaceDescription mapToDriverApi(MessagingInterfaceDescription source);
}
