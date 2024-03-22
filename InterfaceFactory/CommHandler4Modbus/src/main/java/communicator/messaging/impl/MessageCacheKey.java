package communicator.messaging.impl;

import com.smartgridready.ns.v0.MessageFilter;

public class MessageCacheKey {

    private final String topic;
    private final MessageFilter messageFilter; // might be null

    @Override
    public int hashCode() {
        int res = 17;
        res = 31 * res + topic.hashCode();
        res = 31 * res + (messageFilter != null ? messageFilter.hashCode() : 0);
        return res;
    }

    private MessageCacheKey(String topic, MessageFilter messageFilter) {
        this.topic = topic;
        this.messageFilter = messageFilter;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof MessageCacheKey)) {
            return false;
        } else {
            MessageCacheKey toCompare = (MessageCacheKey)obj;
            return this.topic.equals(toCompare.topic)
                    && ((this.messageFilter == null && toCompare.messageFilter == null)
                        || (this.messageFilter != null && this.messageFilter.equals(toCompare.messageFilter)));
        }
    }

    public String getTopic() {
        return topic;
    }

    @SuppressWarnings("unused")
    public MessageFilter getMessageFilter() {
        return messageFilter;
    }

    public static MessageCacheKey of(String topic, MessageFilter messageFilter) {
        if (topic == null) {
            throw new IllegalArgumentException("MessageCacheKey topic must not be null.");
        }
        return new MessageCacheKey(topic, messageFilter);
    }
}
