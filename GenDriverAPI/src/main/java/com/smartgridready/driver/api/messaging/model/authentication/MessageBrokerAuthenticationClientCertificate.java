package com.smartgridready.driver.api.messaging.model.authentication;

/**
 * Represents a message broker authentication configuration using client certificates.
 */
@SuppressWarnings("unused")
public class MessageBrokerAuthenticationClientCertificate {

    private final String keystorePath;

    private final String keystorePassword;

    private final String truststorePath;

    private final String truststorePassword;

    /**
     * Construct.
     * @param keystorePath the path to the key store, containing secrets
     * @param keystorePassword the key store password
     * @param truststorePath the path to the trust store, containing CA certificates
     * @param truststorePassword the trust store
     */
    public MessageBrokerAuthenticationClientCertificate(String keystorePath, String keystorePassword, String truststorePath, String truststorePassword) {
        this.keystorePath = keystorePath;
        this.keystorePassword = keystorePassword;
        this.truststorePath = truststorePath;
        this.truststorePassword = truststorePassword;
    }

    /**
     * Gets the key store path.
     * @return a string
     */
    public String getKeystorePath() {
        return keystorePath;
    }

    /**
     * Gets the key store password.
     * @return a string
     */
    public String getKeystorePassword() {
        return keystorePassword;
    }

    /**
     * Gets the trust store path.
     * @return a string
     */
    public String getTruststorePath() {
        return truststorePath;
    }

    /**
     * Gets the trust store password.
     * @return a string
     */
    public String getTruststorePassword() {
        return truststorePassword;
    }
}
