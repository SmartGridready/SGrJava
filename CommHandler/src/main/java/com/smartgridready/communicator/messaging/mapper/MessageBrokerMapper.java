package com.smartgridready.communicator.messaging.mapper;

import com.smartgridready.ns.v0.MessageBrokerListElement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Defines the interface of a message broker specification mapper.
 */
@Mapper
public interface MessageBrokerMapper {

    /**
     * The default singleton instance.
     */
    MessageBrokerMapper INSTANCE = Mappers.getMapper( MessageBrokerMapper.class );

    /**
     * Maps the message broker specification to its driver equivalent.
     * @param messageBroker the message broker specification
     * @return the driver specification
     */
    com.smartgridready.driver.api.messaging.model.MessageBroker mapToDriverApi(MessageBrokerListElement messageBroker);
}
