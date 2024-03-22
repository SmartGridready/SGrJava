package communicator.messaging.client;

import com.smartgridready.ns.v0.JMESPathFilterType;
import com.smartgridready.ns.v0.MessageFilter;
import com.smartgridready.ns.v0.V0Factory;
import com.smartgridready.ns.v0.XPathFilterType;
import communicator.messaging.api.Message;
import io.vavr.control.Either;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Mqtt5MessagingClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(Mqtt5MessagingClient.class);

    private static final String TEST_TOPIC = "sensors/temperature";

    private static final String TEST_TOPIC_2 = "sensors/humidity";

    private static final String BROKER_HOST = "152f30e8c480481886072e4f8250d91a.s1.eu.hivemq.cloud";

    private static final int BROKER_PORT = 8883;

    @Test
    void sendReceiveSync() throws Exception {

        try (MessagingClient client = new Mqtt5MessagingClient(BROKER_HOST, BROKER_PORT, createClientProperties())) {

            // Set up a thread with a blocking receiver for the message.
            Either<Throwable, Message> result = client.readSync(
                    TEST_TOPIC, Message.of("50K"), TEST_TOPIC, null, 10000);

            assertTrue(result.isRight());

            assertEquals("50K",result.get().

            getPayload());
        }
    }

    @Test
    void sendReceiveSyncWithTopicFilter() throws Exception{

       try (MessagingClient client = new Mqtt5MessagingClient(BROKER_HOST, BROKER_PORT, createClientProperties())) {

           CompletableFuture<Either<Throwable, Message>> asyncMsgSender1 = CompletableFuture.supplyAsync(() ->
                   client.readSync(TEST_TOPIC, Message.of("50K"), TEST_TOPIC, null, 2000));

           CompletableFuture<Either<Throwable, Message>> asyncMsgSender2 = CompletableFuture.supplyAsync(() ->
                   client.readSync(TEST_TOPIC, Message.of("50K"), TEST_TOPIC_2, null, 2000));

           // check futureTopic2 first. Should not receive message from topic one
           Either<Throwable, Message> resultTopic2 = asyncMsgSender2.join();
           assertTrue(resultTopic2.isLeft());

           Either<Throwable, Message> resultTopic1 = asyncMsgSender1.join();
           assertTrue(resultTopic1.isRight());
           assertEquals("50K", resultTopic1.get().getPayload());
       }
    }

    @Test
    void sendReceiveSyncWithMessageFilter() throws Exception {

        try (MessagingClient client = new Mqtt5MessagingClient(BROKER_HOST, BROKER_PORT, createClientProperties())) {

            MessageFilter messageFilter1 = createMessageFilter("1");
            MessageFilter messageFilter2 = createMessageFilter("2");

            // Perform the two reads in parallel
            CompletableFuture<Either<Throwable, Message>> futureFilter1 = CompletableFuture.supplyAsync(() ->
                    client.readSync(
                            TEST_TOPIC,
                            Message.of("{ \"sensorId\": 1, \"value\": \"50K\" }"),
                            TEST_TOPIC,
                            messageFilter1,
                            1000));

            CompletableFuture<Either<Throwable, Message>> futureFilter2 = CompletableFuture.supplyAsync(() ->
                    client.readSync(
                            TEST_TOPIC,
                            Message.of("{ \"sensorId\": 2, \"value\": \"100K\" }"),
                            TEST_TOPIC,
                            messageFilter2,
                            1000));

            // futureFilter1 should receive sensorId=1 message only
            Either<Throwable, Message> result1 = futureFilter1.join();
            assertTrue(result1.isRight());
            assertEquals("{ \"sensorId\": 1, \"value\": \"50K\" }", result1.get().getPayload());

            // futureFilter2 should receive sensorId=2 message only
            Either<Throwable, Message> result2 = futureFilter2.join();
            assertTrue(result2.isRight());
            assertEquals("{ \"sensorId\": 2, \"value\": \"100K\" }", result2.get().getPayload());
        }
    }

    @Test
    void sendAndReceiveSyncTimeout() throws Exception {

        try (MessagingClient client = new Mqtt5MessagingClient(BROKER_HOST, BROKER_PORT, createClientProperties())) {

            Either<Throwable, Message> future = client.readSync(TEST_TOPIC, Message.of("50K"), TEST_TOPIC_2, null, 1000);
            // no message sent to topic
            assertTrue(future.getLeft() instanceof TimeoutException);
        }
    }

    @Test
    void sendAndReceiveSyncUnsupportedFilter() throws Exception {

        try (MessagingClient client = new Mqtt5MessagingClient(BROKER_HOST, BROKER_PORT, createClientProperties())) {
            MessageFilter messageFilter = V0Factory.eINSTANCE.createMessageFilter();
            XPathFilterType xPathFilter = V0Factory.eINSTANCE.createXPathFilterType();
            messageFilter.setXpapathFilter(xPathFilter);

            Either<Throwable, Message> future = client.readSync(
                    TEST_TOPIC, Message.of("Hello"),
                    TEST_TOPIC,
                    messageFilter, 1);

            client.sendSync(TEST_TOPIC, Message.of("Hello"));

            assertTrue(future.getLeft() instanceof OperationNotSupportedException);
        }
    }

    @Test
    void sendReceiveAsynch() throws Exception {

        try (Mqtt5MessagingClient client = new Mqtt5MessagingClient(BROKER_HOST, BROKER_PORT, createClientProperties())) {

            CompletableFuture<Either<Throwable, Message>> future = new CompletableFuture<>();
            client.subscribe(TEST_TOPIC, null, future::complete);

            CompletableFuture<Either<Throwable, Void>> sendAck = client.sendAsynch(TEST_TOPIC, Message.of("46 Kelvin"));
            sendAck.get();

            Awaitility.await().until(() -> {
                Either<Throwable, Message> result = future.get();
                assertTrue(result.isRight());
                assertEquals("46 Kelvin", result.get().getPayload());
                return true;
            });
        }
    }

    @Test
    void sendReceiveAsynchWithMessageFilter() throws Exception {

        try (MessagingClient client = new Mqtt5MessagingClient(BROKER_HOST, BROKER_PORT, createClientProperties())) {

            MessageFilter messageFilter1 = createMessageFilter("1");
            MessageFilter messageFilter2 = createMessageFilter("2");

            List<String> sensor1Results = new ArrayList<>();
            List<String> sensor2Results = new ArrayList<>();

            client.subscribe(TEST_TOPIC, messageFilter1, (resp ->
                    sensor1Results.add(
                            resp.getOrElseGet(t -> Message.of(t.getMessage())).getPayload())));

            client.subscribe(TEST_TOPIC, messageFilter2, (resp ->
                    sensor2Results.add(
                            resp.getOrElseGet(t -> Message.of(t.getMessage())).getPayload())));

            CompletableFuture<Void> writeSensor1 = CompletableFuture.runAsync(() -> {
                for (int i = 0; i < 20; i++) {
                    try {
                        client.sendSync(TEST_TOPIC, Message.of("{ \"sensorId\": 1, \"value\": \"50K\" }"));
                    } catch (Exception e) {
                        LOG.error("setVal() Safe current failed", e);
                    }
                }
            });

            CompletableFuture<Void> writeSensor2 = CompletableFuture.runAsync(() -> {
                for (int i = 0; i < 20; i++) {
                    try {
                        client.sendSync(TEST_TOPIC, Message.of("{ \"sensorId\": 2, \"value\": \"100K\" }"));
                    } catch (Exception e) {
                        LOG.error("setVal() Receive time failed", e);
                    }
                }
            });

            writeSensor1.join();
            writeSensor2.join();

            Awaitility.await().until(() -> sensor1Results.size()==20 && sensor2Results.size()==20);
        }
    }

    private Map<MqttClientProperties, String> createClientProperties() {
        Map<MqttClientProperties, String> properties = new HashMap<>();
        properties.put(MqttClientProperties.USE_SSL, "true");
        properties.put(MqttClientProperties.BASIC_AUTH_USERNAME, "smartgrid");
        properties.put(MqttClientProperties.BASIC_AUTH_PASSWORD, "1SmartGrid!");
        return properties;
    }

    private MessageFilter createMessageFilter(String matchesRegex) {

        MessageFilter messageFilter = V0Factory.eINSTANCE.createMessageFilter();
        JMESPathFilterType jmesPathFilter = V0Factory.eINSTANCE.createJMESPathFilterType();
        jmesPathFilter.setQuery("sensorId");
        jmesPathFilter.setMatchesRegex(matchesRegex);
        messageFilter.setJmespathFilter(jmesPathFilter);
        return messageFilter;
    }
}
