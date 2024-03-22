package communicator.messaging.impl;

import com.smartgridready.ns.v0.DeviceFrame;
import communicator.common.api.StringValue;
import communicator.common.api.Value;
import communicator.common.helper.DeviceDescriptionLoader;
import communicator.common.runtime.GenDriverException;
import io.vavr.control.Either;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SGrMessagingDeviceTest {

    private static final Logger LOG = LoggerFactory.getLogger(SGrMessagingDeviceTest.class);

    private static final String BROKER_HOST = "152f30e8c480481886072e4f8250d91a.s1.eu.hivemq.cloud";

    private static final String BROKER_PORT = "8883";

    private static final String FUNCIONAL_PROFILE = "EVSE_Station1";

    private static final String CHARGING_CURRENT_MIN = "ChargingCurrentMin";
    private static final String CHARGING_CURRENT_MAX = "ChargingCurrentMax";

    @Test
    void getValSynch() throws Exception {

        try (SGrMessagingDevice msgDevice = createMessagingDevice()) {

            Value safeCurrent = msgDevice.getVal(FUNCIONAL_PROFILE, "SafeCurrent");
            LOG.info("Safe current read: {}", safeCurrent.getString());
            assertEquals("12 Amperes", safeCurrent.getString());
        }
    }

    @Test
    void setValSynch() throws Exception {

        try (SGrMessagingDevice msgDevice = createMessagingDevice()) {

            // Will be satisfied on callback
            CompletableFuture<Either<Throwable, Value>> future = new CompletableFuture<>();

            // Prepare a subscription to receive the message published by setVal()
            final String expected = "500ms";
            msgDevice.subscribe(FUNCIONAL_PROFILE, "MaxReceiveTimeSec", future::complete);

            msgDevice.setVal(FUNCIONAL_PROFILE, "MaxReceiveTimeSec", StringValue.of(expected));

            // Wait for the setVal confirmation
            Awaitility.await().until(() -> {
                Either<Throwable, Value> result = future.get();
                assertTrue(result.isRight());
                assertEquals(expected, result.get().getString());
                return true;
            });
        }
    }

    @Test
    void subscribe() throws Exception {

        try (SGrMessagingDevice msgDevice = createMessagingDevice()) {

            List<String> safeCurrents = new ArrayList<>();
            List<String> maxReceiveTimes = new ArrayList<>();

            msgDevice.subscribe(FUNCIONAL_PROFILE, "SafeCurrent",
                    msgReceiveResult -> safeCurrents.add(
                            msgReceiveResult.getOrElseGet(t -> StringValue.of(t.getMessage())).getString()));

            msgDevice.subscribe(FUNCIONAL_PROFILE, "MaxReceiveTimeSec",
                    msgReceiveResult -> maxReceiveTimes.add(
                            msgReceiveResult.getOrElseGet(t -> StringValue.of(t.getMessage())).getString()));

            CompletableFuture<Void> futureSafeCurrent = CompletableFuture.runAsync(() -> {
                for (int i = 0; i < 20; i++) {
                    try {
                        msgDevice.setVal(FUNCIONAL_PROFILE, "SafeCurrent",
                                StringValue.of("{ \"stationId\": 1, \"value\": \"SafeCurrent" + i + "\" }"));
                    } catch (Exception e) {
                        LOG.error("setVal() Safe current failed", e);
                    }
                }
            });

            CompletableFuture<Void> futureReceiveTimes = CompletableFuture.runAsync(() -> {
                for (int i = 0; i < 20; i++) {
                    try {
                        msgDevice.setVal(FUNCIONAL_PROFILE, "MaxReceiveTimeSec", StringValue.of("MaxReceiveTimeSec" + i));
                    } catch (Exception e) {
                        LOG.error("setVal() Receive time failed", e);
                    }
                }
            });

            futureSafeCurrent.join();
            futureReceiveTimes.join();

            Awaitility.await().until(() -> safeCurrents.size()==20 && maxReceiveTimes.size()==20);
            System.out.println(safeCurrents);
            System.out.println(maxReceiveTimes);
        }
    }

    @Test
    void unsubscribe() throws Exception {

        try (SGrMessagingDevice msgDevice = createMessagingDevice()) {

            final SynchronousQueue<Value> resultsMin = new SynchronousQueue<>();
            final SynchronousQueue<Value> resultsMax = new SynchronousQueue<>();

            msgDevice.subscribe(FUNCIONAL_PROFILE, CHARGING_CURRENT_MIN, result -> pushToResultsQueue(resultsMin, result));
            msgDevice.subscribe(FUNCIONAL_PROFILE, CHARGING_CURRENT_MAX, result -> pushToResultsQueue(resultsMax, result));

            // Write values
            String minValMessage = "{ \"limit\": \"min\", \"current\": 54.2  }";
            msgDevice.setVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MIN, StringValue.of(minValMessage));

            String maxValMessage = "{ \"limit\": \"max\", \"current\": 128.7  }";
            msgDevice.setVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MAX, StringValue.of(maxValMessage));

            // Wait for the messages to arrive
            resultsMin.poll(500, TimeUnit.MILLISECONDS);
            resultsMax.poll(500, TimeUnit.MILLISECONDS);

            // Verify read from cache
            assertEquals("54.2", msgDevice.getVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MIN).getString());
            assertEquals("128.7",msgDevice.getVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MAX).getString());

            // Unsubscribe max val
            msgDevice.unsubscribe(FUNCIONAL_PROFILE, CHARGING_CURRENT_MAX);

            // Min value remains in cache and works
            assertEquals("54.2", msgDevice.getVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MIN).getString());

            // Max value has been unsubscribed. No value is available.
            GenDriverException ex = assertThrows(
                    GenDriverException.class,
                    () -> msgDevice.getVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MAX));

            assertTrue(ex.getMessage().contains("No value available"));
            assertTrue(ex.getMessage().contains("Try calling subscribe()"));
        }
    }

    @Test
    void getValFromCache() throws Exception {

        try (SGrMessagingDevice msgDevice = createMessagingDevice()){

            final SynchronousQueue<Value> resultsMin = new SynchronousQueue<>();
            final SynchronousQueue<Value> resultsMax = new SynchronousQueue<>();

            // subscribe() to two datapoints that use the same topic but different filters.
            // They will have different records within the cache.
            msgDevice.subscribe(FUNCIONAL_PROFILE, CHARGING_CURRENT_MIN, result -> pushToResultsQueue(resultsMin, result));
            msgDevice.subscribe(FUNCIONAL_PROFILE, CHARGING_CURRENT_MAX, result -> pushToResultsQueue(resultsMax, result));

            // Write to the same topic with different message payloads regarding the message filter.
            String minValMessage = "{ \"limit\": \"min\", \"current\": 54.2  }";
            msgDevice.setVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MIN, StringValue.of(minValMessage));

            String maxValMessage = "{ \"limit\": \"max\", \"current\": 128.7  }";
            msgDevice.setVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MAX, StringValue.of(maxValMessage));

            // wait until the messages are received
            resultsMin.poll(500, TimeUnit.MILLISECONDS);
            resultsMax.poll(500, TimeUnit.MILLISECONDS);

            // call getVal() to read the min/max values from the cache.
            Value res = msgDevice.getVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MIN);
            assertEquals("54.2", res.getString());

            res = msgDevice.getVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MAX);
            assertEquals("128.7", res.getString());

            // write another value to the max current topic
            maxValMessage = "{ \"limit\": \"max\", \"current\": 100  }";
            msgDevice.setVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MAX, StringValue.of(maxValMessage));

            resultsMax.poll(500, TimeUnit.MILLISECONDS);

            // call getVal() and verify the update
            res = msgDevice.getVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MAX);
            assertEquals("100", res.getString());

        }
    }

    private void pushToResultsQueue(SynchronousQueue<Value> resultQueue, Either<Throwable, Value> result) {
        try {
            if (result.isRight()) {
                LOG.info("Received ChargingCurrent: {}", result.get());
                resultQueue.put(result.get());
            } else {
                LOG.error("Receiving error: " + result.getLeft());
                resultQueue.put(StringValue.of(result.getLeft().getMessage()));
            }
        } catch (Exception e) {
            LOG.error("Queuing error: " + e);
        }
    }

    private SGrMessagingDevice createMessagingDevice() throws GenDriverException {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL deviceDescFile = classloader.getResource("SGr_XX_HiveMQ_MQTT_Cloud.xml");
        DeviceDescriptionLoader<DeviceFrame> loader = new DeviceDescriptionLoader<>();

        Properties properties= new Properties();
        properties.put("host", BROKER_HOST);
        properties.put("port", BROKER_PORT);
        properties.put("username", "smartgrid");
        properties.put("password", "1SmartGrid!");

        DeviceFrame deviceDesc = loader.load(
                "",
                deviceDescFile != null ? deviceDescFile.getPath() : null,
                properties);

        return new SGrMessagingDevice(deviceDesc);
    }

}
