package communicator.messaging.client;

public enum MqttClientProperties {

    USE_SSL("ssl"),
    CLIENT_ID("client.id"),
    BASIC_AUTH_USERNAME("auth.basic.username"),
    BASIC_AUTH_PASSWORD("auth.basic.password"),
    CERT_AUTH_KEYSTORE("auth.cert.keystore"),
    CERT_AUTH_KEYSTORE_PASSWORD("auth.cert.keystore.password"),

    CERT_AUTH_TRUSTSTORE("auth.cert.truststore"),
    CERT_AUTH_TRUSTSTORE_PASSWORD("auth.cert.truststore.password");


    private final String key;

    MqttClientProperties(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }
}
