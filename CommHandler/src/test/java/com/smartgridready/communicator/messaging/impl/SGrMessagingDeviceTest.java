package com.smartgridready.communicator.messaging.impl;

import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.common.helper.DeviceDescriptionLoader;
import com.smartgridready.communicator.common.helper.DriverFactoryLoader;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.messaging.GenMessagingClientFactory;
import com.smartgridready.driver.api.messaging.model.MessagingPlatformType;

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
import static org.junit.jupiter.api.Assertions.fail;

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

            msgDevice.connect();

            // use device to read
            Value safeCurrent = msgDevice.getVal(FUNCIONAL_PROFILE, "SafeCurrent");
            LOG.info("Safe current read: {}", safeCurrent.getString());
            assertEquals(12.34f, safeCurrent.getFloat32());

            // use data point to read
            var msgDataPoint = msgDevice.getDataPoint(FUNCIONAL_PROFILE, "SafeCurrent");
            safeCurrent = msgDataPoint.getVal();
            LOG.info("Safe current read: {}", safeCurrent.getString());
            assertEquals(12.34f, safeCurrent.getFloat32());

            msgDevice.disconnect();
        }
    }

    @Test
    void setValSynch() throws Exception {

        // Perform the setVal() test using the device API
        doSetValTest( (messagingDevice, value, messageReceivedCallback) -> {
                    try {
                        messagingDevice.subscribe(FUNCIONAL_PROFILE, "MaxReceiveTimeSec", messageReceivedCallback::complete);
                        messagingDevice.setVal(FUNCIONAL_PROFILE, "MaxReceiveTimeSec", value);
                    } catch (Exception e) {
                        fail("setValSynch() test using the device API failed: " + e.getMessage());
                    }
                });

        // Perform the setVal() test using the data point API
        doSetValTest( (messagingDevice, value, messageReceivedCallback) -> {
            try {
                var dataPoint = messagingDevice.getDataPoint(FUNCIONAL_PROFILE, "MaxReceiveTimeSec");
                dataPoint.subscribe(messageReceivedCallback::complete);
                dataPoint.setVal(value);
            } catch (Exception e) {
                fail("setValSynch() test using the data point API failed: " + e.getMessage());
            }
        });


    }

    private interface SetValOperation {
        void apply(SGrMessagingDevice messagingDevice,
                   StringValue value,
                   CompletableFuture<Either<Throwable, Value>> messageReceivedCallback);
    }

    private void doSetValTest(SetValOperation setValOperation) throws Exception {

        try (SGrMessagingDevice msgDevice = createMessagingDevice()) {

            msgDevice.connect();

            // Will be satisfied on callback
            CompletableFuture<Either<Throwable, Value>> future = new CompletableFuture<>();

            // Prepare a subscription to receive the message published by setVal()
            final String expected = "500ms";

            // Prepare and perform the setVal operation
            setValOperation.apply(msgDevice, StringValue.of(expected), future);

            // Wait for the setVal confirmation (echo from broker)
            Awaitility.await().until(() -> {
                Either<Throwable, Value> result = future.get();
                assertTrue(result.isRight());
                assertEquals(expected, result.get().getString());
                return true;
            });

            msgDevice.disconnect();
        }
    }


    @Test
    void subscribe() throws Exception {

        // Test with device
        doSubscribeTest((msgDevice, dataPointName, collectedValues) -> {
            try {
                msgDevice.subscribe(FUNCIONAL_PROFILE, dataPointName,
                        msgReceiveResult -> collectedValues.add(
                                msgReceiveResult.getOrElseGet(t -> StringValue.of(t.getMessage())).getString()));
            } catch (Exception e) {
                fail("subscribe() test using the device failed. " + e.getMessage());
            }
        });

        // Test with data point
        doSubscribeTest((msgDevice, dataPointName, collectedValues) -> {
            try {
                var dataPoint = msgDevice.getDataPoint(FUNCIONAL_PROFILE, dataPointName);
                dataPoint.subscribe(
                        msgReceiveResult -> collectedValues.add(
                                msgReceiveResult.getOrElseGet(t -> StringValue.of(t.getMessage())).getString()));
            } catch (Exception e) {
                fail("subscribe() test using the data point failed. " + e.getMessage());
            }
        });
    }

    private interface SubscribeOperation {
        void apply(SGrMessagingDevice messagingDevice, String dataPointName, List<String> collectedValues);
    }

    private void doSubscribeTest(SubscribeOperation subscribeOperation) throws Exception {

        try (SGrMessagingDevice msgDevice = createMessagingDevice()) {

            msgDevice.connect();

            List<String> safeCurrents = new ArrayList<>();
            List<String> maxReceiveTimes = new ArrayList<>();

            subscribeOperation.apply(msgDevice, "SafeCurrent", safeCurrents);
            subscribeOperation.apply(msgDevice, "MaxReceiveTimeSec", maxReceiveTimes);

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

            Awaitility.await().until(() -> safeCurrents.size() == 20 && maxReceiveTimes.size() == 20);
            System.out.println(safeCurrents);
            System.out.println(maxReceiveTimes);

            msgDevice.disconnect();
        }
    }


    @Test
    void unsubscribe() throws Exception {

        // Test with device API
        doUnsubscribeTest(msgDevice -> {
            try {
                msgDevice.unsubscribe(FUNCIONAL_PROFILE, CHARGING_CURRENT_MAX);
            } catch (Exception e) {
                fail("unsubscribe() test using device failed. " + e.getMessage());
            }
        });

        // Test with datapoint API
        doUnsubscribeTest(msgDevice -> {
            try {
                var dataPoint = msgDevice.getDataPoint(FUNCIONAL_PROFILE, CHARGING_CURRENT_MAX);
                dataPoint.unsubscribe();
            } catch (Exception e) {
                fail("unsubscribe() test using data point failed. " + e.getMessage());
            }
        });
    }

    private interface UnsubscribeOperation {
        void apply(SGrMessagingDevice msgDevice);
    }

    private void doUnsubscribeTest(UnsubscribeOperation unsubscribeOperation) throws Exception {

        try (SGrMessagingDevice msgDevice = createMessagingDevice()) {

            msgDevice.connect();

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
            unsubscribeOperation.apply(msgDevice);

            // Min value remains in cache and works
            assertEquals("54.2", msgDevice.getVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MIN).getString());

            // Max value has been unsubscribed. No value is available.
            GenDriverException ex = assertThrows(
                    GenDriverException.class,
                    () -> msgDevice.getVal(FUNCIONAL_PROFILE, CHARGING_CURRENT_MAX));

            assertTrue(ex.getMessage().contains("No value available"));
            assertTrue(ex.getMessage().contains("Try calling subscribe()"));

            msgDevice.disconnect();
        }
    }

    @Test
    void readAllValuesFromDevice() throws Exception {

        try (SGrMessagingDevice msgDevice = createMessagingDevice()) {

            msgDevice.connect();

            var readValues = msgDevice.getDeviceInfo().getValues();
            readValues.forEach( dataPointValue ->
                    LOG.info("Read dataPoint: {}", dataPointValue)
            );
            assertEquals(4, readValues.size());
            assertEquals("profile: EVSE_Station1, dataPoint: SafeCurrent, value: 12.34", readValues.get(0).toString());
            assertEquals("profile: EVSE_Station1, dataPoint: MaxReceiveTimeSec, value: read", readValues.get(1).toString());
            assertEquals("profile: EVSE_Station1, dataPoint: ChargingCurrentMin, value: No value available for functionalProfile=EVSE_Station1, dataPoint=ChargingCurrentMin. Try calling subscribe()", readValues.get(2).toString());
            assertEquals("profile: EVSE_Station1, dataPoint: ChargingCurrentMax, value: No value available for functionalProfile=EVSE_Station1, dataPoint=ChargingCurrentMax. Try calling subscribe()", readValues.get(3).toString());
            msgDevice.disconnect();
        }
    }


    @Test
    void getValFromCache() throws Exception {

        try (SGrMessagingDevice msgDevice = createMessagingDevice()) {

            msgDevice.connect();

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

            msgDevice.disconnect();
        }
    }

    private void pushToResultsQueue(SynchronousQueue<Value> resultQueue, Either<Throwable, Value> result) {
        try {
            if (result.isRight()) {
                LOG.info("Received ChargingCurrent: {}", result.get());
                resultQueue.put(result.get());
            } else {
                LOG.error("Receiving error: {}", result.getLeft().getMessage());
                resultQueue.put(StringValue.of(result.getLeft().getMessage()));
            }
        } catch (Exception e) {
            LOG.error("Queuing error: {}", e.getMessage());
        }
    }

    private SGrMessagingDevice createMessagingDevice() throws GenDriverException {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL deviceDescFile = classloader.getResource("SGr_XX_HiveMQ_MQTT_Cloud.xml");
        DeviceDescriptionLoader loader = new DeviceDescriptionLoader();

        Properties properties= new Properties();
        properties.put("host", BROKER_HOST);
        properties.put("port", BROKER_PORT);
        properties.put("username", "smartgrid");
        properties.put("password", "1SmartGrid!");

        DeviceFrame deviceDesc = loader.load(
                "",
                deviceDescFile != null ? deviceDescFile.getPath() : null,
                properties);

        GenMessagingClientFactory factory = DriverFactoryLoader.getMessagingDriver(MessagingPlatformType.MQTT5);
        return new SGrMessagingDevice(deviceDesc, factory);
    }

}
