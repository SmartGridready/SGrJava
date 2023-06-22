package communicator.async;


import communicator.async.process.Parallel;
import communicator.async.process.ExecStatus;
import communicator.async.process.Processor;
import communicator.async.process.ReadExec;
import communicator.async.process.Sequence;
import communicator.async.process.WriteExec;
import communicator.common.runtime.GenDriverModbusException;
import communicator.impl.SGrModbusDevice;
import communicator.restapi.exception.RestApiAuthenticationException;
import communicator.restapi.impl.SGrRestApiDevice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
class AsyncDataStructureTest {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncDataStructureTest.class);
    @Mock
    SGrModbusDevice wagoModbusDevice;
    @Mock
    SGrRestApiDevice clemapRestApiDevice;

    @Mock
    SGrRestApiDevice clemapRestApiDevice_2;

    @Mock
    SGrModbusDevice garoModbusDevice_A;
    @Mock
    SGrModbusDevice garoModbusDevice_B;


    private String withDelay(long delay, String value) throws Exception {
        Thread.sleep(delay);
        LOG.debug("Delay {}ms is over.", delay);
        return value;
    }

    @Test
    void buildAndRunDataStructure() throws Exception {
        initStubs();
        doBuildAndRunDatstructureTest(ExecStatus.SUCCESS, null);
    }

    @Test
    void buildAndRunDataStructureWithException() throws Exception {
        initStubsWithException("DEVICE ERROR");
        doBuildAndRunDatstructureTest(ExecStatus.ERROR, "DEVICE ERROR");
    }

    private void doBuildAndRunDatstructureTest(ExecStatus expectedStatus, String expectedExceptionMessage) {

        // Setup READ tasks
        ReadExec<String> wago_voltageAC_l1       = new ReadExec<>( "VoltageAC", "VoltageL1",  wagoModbusDevice::getVal);
        ReadExec<String> wago_voltageAC_l2       = new ReadExec<>( "VoltageAC", "VoltageL2",  wagoModbusDevice::getVal);
        ReadExec<String> wago_voltageAC_l3       = new ReadExec<>( "VoltageAC", "VoltageL3",  wagoModbusDevice::getVal);
        ReadExec<String> clemap_actPowerAC_tot   = new ReadExec<>( "ActivePowerAC", "ActivePowerACtot", clemapRestApiDevice::getVal);
        ReadExec<String> clemap_actPowerAC_tot_2 = new ReadExec<>("ActivePowerAC", "ActivePowerACtot",       clemapRestApiDevice_2::getVal);

        // Setup WRITE tasks
        WriteExec<String> garo_wallbox_A_hems_curr_lim = new WriteExec<>("Curtailment", "HemsCurrentLimit", garoModbusDevice_A::setVal);
        WriteExec<String> garo_wallbox_B_hems_curr_lim = new WriteExec<>("Curtailment", "HemsCurrentLimit", garoModbusDevice_B::setVal);

        // Wire tasks
        Processor readChain = new Parallel()        // 2000
                .add( new Sequence()                // 1500
                        .add(wago_voltageAC_l1)
                        .add(wago_voltageAC_l2)
                        .add(wago_voltageAC_l3))
                .add( new Parallel()                    // 2000
                        .add(clemap_actPowerAC_tot)     // 750
                        .add(clemap_actPowerAC_tot_2))  // 2000
                .await( wago_voltageAC_l1,
                        wago_voltageAC_l2,
                        wago_voltageAC_l3,
                        clemap_actPowerAC_tot,
                        clemap_actPowerAC_tot_2);

        Processor writeChain = new Parallel()
                        .add(garo_wallbox_A_hems_curr_lim)
                        .add(garo_wallbox_B_hems_curr_lim)
                .await(garo_wallbox_A_hems_curr_lim,
                        garo_wallbox_B_hems_curr_lim);

        // Run readChain
        readChain.process();

        // Get results from read-chain.
        // Example: wago_voltageAC_l1.getReadValue();
        // Do some calculations and determine new control values:
        garo_wallbox_A_hems_curr_lim.setWriteValue("10A");
        garo_wallbox_B_hems_curr_lim.setWriteValue("5A");
        // Process the write-chain:
        writeChain.process();


        LOG.info(wago_voltageAC_l1.toString());
        LOG.info(wago_voltageAC_l2.toString());
        LOG.info(wago_voltageAC_l3.toString());
        LOG.info(clemap_actPowerAC_tot.toString());
        LOG.info(clemap_actPowerAC_tot_2.toString());
        LOG.info(garo_wallbox_A_hems_curr_lim.toString());
        LOG.info(garo_wallbox_B_hems_curr_lim.toString());

        verifyResultsAndTiming(
                expectedStatus,
                expectedExceptionMessage,
                wago_voltageAC_l1,
                wago_voltageAC_l2,
                wago_voltageAC_l3,
                clemap_actPowerAC_tot,
                clemap_actPowerAC_tot_2,
                garo_wallbox_A_hems_curr_lim,
                garo_wallbox_B_hems_curr_lim);

        wago_voltageAC_l1.cleanup();
        wago_voltageAC_l2.cleanup();
        wago_voltageAC_l3.cleanup();
        clemap_actPowerAC_tot.cleanup();
        garo_wallbox_A_hems_curr_lim.cleanup();
        garo_wallbox_B_hems_curr_lim.cleanup();
    }

    private static void verifyResultsAndTiming(ExecStatus expectedStatus,
                                               String expectedExceptionMessage,
                                               ReadExec<String> wago_voltageAC_l1,
                                               ReadExec<String> wago_voltageAC_l2,
                                               ReadExec<String> wago_voltageAC_l3,
                                               ReadExec<String> clemap_actPowerAC_tot,
                                               ReadExec<String> clemap_actPowerAC_tot_2,
                                               WriteExec<String> garo_wallbox_A_hems_curr_lim,
                                               WriteExec<String> garo_wallbox_B_hems_curr_lim) {
        // Status
        assertEquals(expectedStatus, wago_voltageAC_l1.getExecStatus());
        assertEquals(expectedStatus, wago_voltageAC_l2.getExecStatus());
        assertEquals(expectedStatus, wago_voltageAC_l3.getExecStatus());
        assertEquals(expectedStatus, clemap_actPowerAC_tot.getExecStatus());
        assertEquals(expectedStatus, clemap_actPowerAC_tot_2.getExecStatus());
        assertEquals(expectedStatus, garo_wallbox_A_hems_curr_lim.getExecStatus());


        if (expectedExceptionMessage== null) {
            // Happy case
            assertNull(wago_voltageAC_l1.getExecThrowable());
            assertNull(wago_voltageAC_l2.getExecThrowable());
            assertNull(wago_voltageAC_l3.getExecThrowable());
            assertNull(clemap_actPowerAC_tot.getExecThrowable());
            assertNull(garo_wallbox_A_hems_curr_lim.getExecThrowable());
            assertNull(garo_wallbox_B_hems_curr_lim.getExecThrowable());
            assertEquals("220V",  wago_voltageAC_l1.getReadValue());
            assertEquals("20kWh", clemap_actPowerAC_tot.getReadValue());
            assertEquals("50kWh", clemap_actPowerAC_tot_2.getReadValue());

            // Sequential read stuff
            // Timing
            Instant start = wago_voltageAC_l1.getRequestTime();
            assertTrue(start.plusMillis(500).isBefore(wago_voltageAC_l1.getResponseTime()));
            assertTrue(start.plusMillis(600).isAfter(wago_voltageAC_l1.getResponseTime()));
            assertTrue(start.plusMillis(1000).isBefore(wago_voltageAC_l2.getResponseTime()));
            assertTrue(start.plusMillis(1100).isAfter(wago_voltageAC_l2.getResponseTime()));
            assertTrue(start.plusMillis(1500).isBefore(wago_voltageAC_l3.getResponseTime()));
            assertTrue(start.plusMillis(1600).isAfter(wago_voltageAC_l3.getResponseTime()));
            // Parallel read stuff
            assertTrue(start.plusMillis(750).isBefore(clemap_actPowerAC_tot.getResponseTime()));
            assertTrue(start.plusMillis(1000).isAfter(clemap_actPowerAC_tot.getResponseTime()));
            assertTrue(start.plusMillis( 2000).isBefore(clemap_actPowerAC_tot_2.getResponseTime()));
            assertTrue(start.plusMillis(2200).isAfter(clemap_actPowerAC_tot_2.getResponseTime()));

            // Parallel write stuff (sequential after read stuff)
            // Assert that writeCycle is performed after readCycle (await works...)
            assertTrue(start.plusMillis(2000).isBefore(garo_wallbox_A_hems_curr_lim.getRequestTime()));

            start = garo_wallbox_B_hems_curr_lim.getRequestTime();
            assertTrue(start.plusMillis(500).isBefore(garo_wallbox_A_hems_curr_lim.getResponseTime()));
            assertTrue(start.plusMillis(250).isBefore(garo_wallbox_B_hems_curr_lim.getResponseTime()));

        } else {
            // Exception case
            assertEquals(expectedExceptionMessage, wago_voltageAC_l1.getExecThrowable().getMessage());
            assertEquals(expectedExceptionMessage, wago_voltageAC_l2.getExecThrowable().getMessage());
            assertEquals(expectedExceptionMessage, wago_voltageAC_l3.getExecThrowable().getMessage());
            assertEquals(expectedExceptionMessage, clemap_actPowerAC_tot.getExecThrowable().getMessage());
            assertEquals(expectedExceptionMessage, garo_wallbox_A_hems_curr_lim.getExecThrowable().getMessage());
            assertEquals(expectedExceptionMessage, garo_wallbox_B_hems_curr_lim.getExecThrowable().getMessage());
            assertTrue( wago_voltageAC_l1.getExecThrowable() instanceof GenDriverModbusException);
            assertTrue( clemap_actPowerAC_tot.getExecThrowable() instanceof RestApiAuthenticationException);
            assertTrue( garo_wallbox_A_hems_curr_lim.getExecThrowable() instanceof GenDriverModbusException);
        }
    }

    private void initStubs() throws Exception {
        when(wagoModbusDevice.getVal(any(), any())).thenAnswer(
                (Answer<String>) invocation -> withDelay(500, "220V"));

        when(clemapRestApiDevice.getVal(any(), any())).thenAnswer(
                (Answer<String>) invocation -> withDelay(750, "20kWh"));

        when(clemapRestApiDevice_2.getVal(any(), any())).thenAnswer(
                (Answer<String>) invocation -> withDelay(2000, "50kWh"));

        when(garoModbusDevice_A.setVal(any(), any(), any())).thenAnswer(
                (Answer<String>) invocation ->  withDelay(500, "OK"));

        when(garoModbusDevice_B.setVal(any(), any(), any())).thenAnswer(
                (Answer<String>) invocation ->  withDelay(250, "OK"));
    }

    private void initStubsWithException(String errorMessage) throws Exception {

        when(wagoModbusDevice.getVal(any(), any())).thenAnswer(
                (Answer<String>) invocation -> { withDelay(500, "220V"); throw new GenDriverModbusException(errorMessage);});

        when(clemapRestApiDevice.getVal(any(), any())).thenAnswer(
                (Answer<String>) invocation -> { withDelay(750, "20kWh"); throw new RestApiAuthenticationException(errorMessage);});

        when(clemapRestApiDevice_2.getVal(any(), any())).thenAnswer(
                (Answer<String>) invocation -> { withDelay(2000, "50kWh"); throw new RestApiAuthenticationException(errorMessage);});

        when(garoModbusDevice_A.setVal(any(), any(), any())).thenAnswer(
                (Answer<String>) invocation ->  { withDelay(1000, "OK"); throw new GenDriverModbusException(errorMessage);});

        when(garoModbusDevice_B.setVal(any(), any(), any())).thenAnswer(
                (Answer<String>) invocation ->  { withDelay(1000, "OK"); throw new GenDriverModbusException(errorMessage);});
    }
}