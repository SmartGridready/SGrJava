package communicator.messaging.api;

public class Message {

    private final String key;
    private final String payload;

    private Message(String payload) {
        this.key = null;
        this.payload = payload;
    }

    public Message(String key, String payload) {
        this.key = key;
        this.payload = payload;
    }

    public String getKey() {
        return key;
    }

    public String getPayload() {
        return payload;
    }

    public static Message of(String payload) {
        return new Message(payload);
    }

    public static Message of(String key, String payload) {
        return new Message(key, payload);
    }
}
