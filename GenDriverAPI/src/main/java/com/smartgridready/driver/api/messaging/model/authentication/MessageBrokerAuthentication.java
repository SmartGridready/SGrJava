package com.smartgridready.driver.api.messaging.model.authentication;

/**
 * Contains message broker authentication configurations.
 */
@SuppressWarnings("unused")
public class MessageBrokerAuthentication {

    private final MessageBrokerAuthenticationBasic basicAuthentication;

    private final MessageBrokerAuthenticationClientCertificate clientCertificateAuthentication;

    /**
     * Construct all configurations.
     * @param basicAuthentication a basic authentication configuration
     * @param clientCertificateAuthentication a client-certificate-based authentication configuration
     */
    public MessageBrokerAuthentication(MessageBrokerAuthenticationBasic basicAuthentication, MessageBrokerAuthenticationClientCertificate clientCertificateAuthentication) {
        this.basicAuthentication = basicAuthentication;
        this.clientCertificateAuthentication = clientCertificateAuthentication;
    }

    /**
     * Gets the basic authentication configuration.
     * @return an instance of {@code MessageBrokerAuthenticationBasic}
     */
    public MessageBrokerAuthenticationBasic getBasicAuthentication() {
        return basicAuthentication;
    }

    /**
     * Gets the client-certificate-based configuration.
     * @return an instance of {@code MessageBrokerAuthenticationClientCertificate}
     */
    public MessageBrokerAuthenticationClientCertificate getClientCertificateAuthentication() {
        return clientCertificateAuthentication;
    }
}
