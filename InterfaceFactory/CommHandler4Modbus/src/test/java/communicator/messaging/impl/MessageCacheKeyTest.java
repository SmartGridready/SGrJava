package communicator.messaging.impl;

import com.smartgridready.ns.v0.MessageFilter;
import com.smartgridready.ns.v0.V0Factory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MessageCacheKeyTest {

    @Test
    void equalsAndHashTopicOnly() {
        MessageCacheKey key1 = MessageCacheKey.of("test-topic", null);
        MessageCacheKey key2 = MessageCacheKey.of("test-topic", null);
        assertEquals(key1, key2);
        assertEquals(key1.hashCode(), key2.hashCode());

        key2 = MessageCacheKey.of("test", null);
        assertNotEquals(key1, key2);
        assertNotEquals(key1.hashCode(), key2.hashCode());
    }

    @Test
    void equalsAndHasTopicAndFilter() {

        MessageFilter filter1 = V0Factory.eINSTANCE.createMessageFilter();
        MessageFilter filter2 = V0Factory.eINSTANCE.createMessageFilter();

        MessageCacheKey key1 = MessageCacheKey.of("test-topic", filter1);
        MessageCacheKey key2 = MessageCacheKey.of("test-topic", filter1);
        assertEquals(key1, key2);
        assertEquals(key1.hashCode(), key2.hashCode());

        key2 = MessageCacheKey.of("test-topic", filter2);
        assertNotEquals(key1, key2);
        assertNotEquals(key1.hashCode(), key2.hashCode());
    }

    @Test
    void equalsCompareToNull() {
        MessageCacheKey key = MessageCacheKey.of("test-topic", null);
        assertNotEquals(null, key);
    }
}
