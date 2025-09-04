package com.smartgridready.communicator.messaging.mapper;

import com.smartgridready.driver.api.messaging.model.MessageBroker;
import com.smartgridready.ns.v0.MessageBrokerList;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a message broker list mapper.
 */
public class BrokerListMapper {

    /**
     * Constructs a new instance.
     */
    BrokerListMapper() {}

    /**
     * Maps the message broker list to a regular list.
     * @param messageBrokerList the message broker list
     * @return a list
     */
    List<MessageBroker> mapToDriverApi(MessageBrokerList messageBrokerList) {
        List<MessageBroker> result = new ArrayList<>();
        messageBrokerList.getMessageBrokerListElement().forEach(messageBroker ->
                result.add(MessageBrokerMapper.INSTANCE.mapToDriverApi(messageBroker))
        );
        return result;
    }
}
