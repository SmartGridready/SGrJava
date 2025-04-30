package com.smartgridready.driver.api.messaging.model;

/**
 * Implements a message broker configuration.
 */
@SuppressWarnings("unused")
public class MessageBroker {

    private final String host;

    private final String port;

    private final boolean tls;

    private final boolean tlsVerifyCertificate;

    /**
     * Construct.
     * @param host the message broker host address
     * @param port the message broker TCP port
     * @param tls use TLS when true
     * @param tlsVerifyCertificate verifies TLS certificate when true, otherwise not
     */
    public MessageBroker(String host, String port, boolean tls, boolean tlsVerifyCertificate) {
        this.host = host;
        this.port = port;
        this.tls = tls;
        this.tlsVerifyCertificate = tlsVerifyCertificate;
    }

    /**
     * Gets the host address.
     * @return a string
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets the TCP port.
     * @return a string with numeric value
     */
    public String getPort() {
        return port;
    }

    /**
     * Tells if TLS is used.
     * @return true if used, false otherwise
     */
    public boolean isTls() {
        return tls;
    }

    /**
     * Tells if TLS certificate verification is enabled.
     * @return true if verification enabled, false otherwise
     */
    public boolean isTlsVerifyCertificate() {
        return tlsVerifyCertificate;
    }
}
