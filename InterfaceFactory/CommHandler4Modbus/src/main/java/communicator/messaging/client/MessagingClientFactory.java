package communicator.messaging.client;

import com.smartgridready.ns.v0.MessageBrokerAuthentication;
import com.smartgridready.ns.v0.MessageBrokerListElement;
import com.smartgridready.ns.v0.MessagingInterfaceDescription;
import org.apache.commons.lang3.NotImplementedException;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class MessagingClientFactory {

    private MessagingClientFactory() {}

    public static MessagingClient createClient(MessagingInterfaceDescription interfaceDescription) {

        if (interfaceDescription.getPlatform() == null) {
            throw new IllegalArgumentException("No messaging platform defined in EI-XML");
        }

        switch (interfaceDescription.getPlatform()) {
            case MQTT5:
                return createMqtt5Client(interfaceDescription);
            case KAFKA:
                throw new NotImplementedException("Kafka messaging client not supprted yet.");
            default:
                throw new IllegalArgumentException("Unsupported messaging platform type: " + interfaceDescription.getPlatform().getName());
        }
    }

    private static Mqtt5MessagingClient createMqtt5Client(MessagingInterfaceDescription interfaceDescription) {

        if (interfaceDescription.getMessageBrokerList().getMessageBrokerListElement().isEmpty()) {
            throw new IllegalArgumentException("No message broker defined in EI-XML");
        }
        // Take first broker
        MessageBrokerListElement messageBroker = interfaceDescription.getMessageBrokerList().getMessageBrokerListElement().get(0);

        Map<MqttClientProperties, String> clientProperties = new EnumMap<>(MqttClientProperties.class);
        if (interfaceDescription.getMessageBrokerAuthentication() != null) {
            evaluateAuthenticationProperties(clientProperties, interfaceDescription);
        }

        if (interfaceDescription.getClientId() != null) {
            clientProperties.put(MqttClientProperties.CLIENT_ID, interfaceDescription.getClientId());
        }

        return new Mqtt5MessagingClient(messageBroker.getHost(), Integer.parseInt(messageBroker.getPort()), clientProperties);
    }

    private static void evaluateAuthenticationProperties(Map<MqttClientProperties, String> properties, MessagingInterfaceDescription interfaceDescription) {

        MessageBrokerAuthentication authentication = interfaceDescription.getMessageBrokerAuthentication();
        if (authentication != null && authentication.getBasicAuthentication() != null) {
            properties.put(MqttClientProperties.BASIC_AUTH_USERNAME, Optional.ofNullable(authentication.getBasicAuthentication().getUsername()).orElse(""));
            properties.put(MqttClientProperties.BASIC_AUTH_PASSWORD, Optional.ofNullable(authentication.getBasicAuthentication().getPassword()).orElse(""));
            properties.put(MqttClientProperties.USE_SSL, String.valueOf(true));
        }

        if(authentication != null && authentication.getClientCertificateAuthentication() != null) {
            throw new NotImplementedException("Client certificate authentication not supported yet");
        }
    }

}
