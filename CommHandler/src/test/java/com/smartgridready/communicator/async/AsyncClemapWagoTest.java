package com.smartgridready.communicator.async;

import com.smartgridready.ns.v0.DeviceFrame;

import com.smartgridready.communicator.async.process.ExecStatus;
import com.smartgridready.communicator.async.process.Parallel;
import com.smartgridready.communicator.async.process.Processor;
import com.smartgridready.communicator.async.process.ReadExec;
import com.smartgridready.communicator.async.process.Sequence;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.common.helper.DeviceDescriptionLoader;
import com.smartgridready.communicator.common.helper.DriverFactoryLoader;
import com.smartgridready.communicator.modbus.impl.SGrModbusDevice;
import com.smartgridready.communicator.rest.impl.SGrRestApiDevice;
import com.smartgridready.driver.api.http.GenHttpClientFactory;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.internal.schedulers.IoScheduler;
import io.reactivex.rxjava3.internal.schedulers.RxThreadFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("Needs a real device")
public class AsyncClemapWagoTest {
    public static final Logger LOG = LoggerFactory.getLogger(AsyncClemapWagoTest.class);

    private static final String XML_BASE_DIR = "../../SGrSpecifications/XMLInstances/ExtInterfaces";

    private static SGrModbusDevice wagoDevice;

    private static SGrRestApiDevice clemapDevice;

    private static GenDriverAPI4Modbus wagoDriver;

    @BeforeAll
    public static void createDevices() throws Exception {
        wagoDevice = createWagoDevice();
        wagoDevice.connect();

        clemapDevice = createClemapDevice();
        clemapDevice.connect();
    }

    @AfterAll
    public static void closeDevices() throws Exception {
        wagoDevice.disconnect();
        clemapDevice.disconnect();
    }

    @Test
    void asyncRealClemapWago() {

        Scheduler schedulerMaxPrio = new IoScheduler(new RxThreadFactory("MAX_PRIO_SCHEDULER", Thread.MAX_PRIORITY));

        // Setup tasks
        ReadExec<Value> wago_voltageAC_l1 = new ReadExec<>( "VoltageAC", "VoltageL1",  wagoDevice::getVal);
        ReadExec<Value> wago_voltageAC_l2 = new ReadExec<>( "VoltageAC", "VoltageL2",  wagoDevice::getVal);
        ReadExec<Value> wago_voltageAC_l3 = new ReadExec<>( "VoltageAC", "VoltageL3",  wagoDevice::getVal);
        ReadExec<Value> clemap_actPowerAC_tot = new ReadExec<>( "ActivePowerAC", "ActivePowerACtot", clemapDevice::getVal, schedulerMaxPrio);
        ReadExec<Value> clemap_actPowerAC_L1  = new ReadExec<>( "ActivePowerAC", "ActivePowerACL1", clemapDevice::getVal, schedulerMaxPrio);

        // Wire tasks
        Processor readChain = new Parallel()        // 1500
                .add( new Sequence()                // 1500
                        .add(wago_voltageAC_l1)
                        .add(wago_voltageAC_l2)
                        .add(wago_voltageAC_l3))
                .add( new Parallel()                //  750
                        .add(clemap_actPowerAC_tot)
                        .add(clemap_actPowerAC_L1))
                .await( wago_voltageAC_l1,
                        wago_voltageAC_l2,
                        wago_voltageAC_l3,
                        clemap_actPowerAC_tot,
                        clemap_actPowerAC_L1);

        Instant startTime = Instant.now();
        readChain.process();
        Instant endTime = Instant.now();
        LOG.info("Delta T: {}ms", endTime.toEpochMilli() - startTime.toEpochMilli());

        LOG.info(wago_voltageAC_l1.toString());
        LOG.info(wago_voltageAC_l2.toString());
        LOG.info(wago_voltageAC_l3.toString());
        LOG.info(clemap_actPowerAC_tot.toString());
        LOG.info(clemap_actPowerAC_L1.toString());

        assertEquals(ExecStatus.SUCCESS, wago_voltageAC_l1.getExecStatus());
        assertEquals(ExecStatus.SUCCESS, wago_voltageAC_l2.getExecStatus());
        assertEquals(ExecStatus.SUCCESS, wago_voltageAC_l3.getExecStatus());
        assertEquals(ExecStatus.SUCCESS, clemap_actPowerAC_tot.getExecStatus());
        assertEquals(ExecStatus.SUCCESS, clemap_actPowerAC_L1.getExecStatus());
    }

    @Test
    void asyncRealClemapWagoSerial() {

        // Setup tasks
        ReadExec<Value> wago_voltageAC_l1 = new ReadExec<>( "VoltageAC", "VoltageL1",  wagoDevice::getVal);
        ReadExec<Value> wago_voltageAC_l2 = new ReadExec<>( "VoltageAC", "VoltageL2",  wagoDevice::getVal);
        ReadExec<Value> wago_voltageAC_l3 = new ReadExec<>( "VoltageAC", "VoltageL3",  wagoDevice::getVal);
        ReadExec<Value> clemap_actPowerAC_tot = new ReadExec<>( "ActivePowerAC", "ActivePowerACtot", clemapDevice::getVal);
        ReadExec<Value> clemap_actPowerAC_L1  = new ReadExec<>( "ActivePowerAC", "ActivePowerACL1", clemapDevice::getVal);

        // Wire tasks
        Processor readChain = new Sequence()       // 1500
                        .add(wago_voltageAC_l1)
                        .add(wago_voltageAC_l2)
                        .add(wago_voltageAC_l3)
                        .add(clemap_actPowerAC_tot)
                        .add(clemap_actPowerAC_L1)
                .await( wago_voltageAC_l1,
                        wago_voltageAC_l2,
                        wago_voltageAC_l3,
                        clemap_actPowerAC_tot,
                        clemap_actPowerAC_L1);

        Instant startTime = Instant.now();
        readChain.process();
        Instant endTime = Instant.now();
        LOG.info("Delta T: {}ms", endTime.toEpochMilli() - startTime.toEpochMilli());
        LOG.info(wago_voltageAC_l1.toString());
        LOG.info(wago_voltageAC_l2.toString());
        LOG.info(wago_voltageAC_l3.toString());
        LOG.info(clemap_actPowerAC_tot.toString());
        LOG.info(clemap_actPowerAC_L1.toString());

        assertEquals(ExecStatus.SUCCESS, wago_voltageAC_l1.getExecStatus());
        assertEquals(ExecStatus.SUCCESS, wago_voltageAC_l2.getExecStatus());
        assertEquals(ExecStatus.SUCCESS, wago_voltageAC_l3.getExecStatus());
        assertEquals(ExecStatus.SUCCESS, clemap_actPowerAC_tot.getExecStatus());
        assertEquals(ExecStatus.SUCCESS, clemap_actPowerAC_L1.getExecStatus());
    }

    private static SGrModbusDevice createWagoDevice() throws Exception {
        DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
        DeviceFrame deviceDesc = loader.load( XML_BASE_DIR, "SGr_04_0014_0000_WAGO_SmartMeterV0.2.1.xml");

        GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getModbusDriver();
        wagoDriver = factory.createRtuTransport("COM3", 19200);
        wagoDriver.connect();
        return new SGrModbusDevice(deviceDesc, wagoDriver);
    }

    private static SGrRestApiDevice createClemapDevice() throws Exception {

        Properties props = new Properties();
        props.put("username", "hfurrer@ergonomics.ch");
        props.put("password", "Holdrio99");
        props.put("baseUri", "https://cloud.clemap.com:3032");
        props.put("sensor_id", "63343431ecf2cf013a1e5a9f");

        DeviceFrame deviceDesc = new DeviceDescriptionLoader()
                .load(XML_BASE_DIR, "SGr_02_0018_CLEMAP_EIcloudEnergyMonitor_V1.0.0.xml", props);

        GenHttpClientFactory factory = DriverFactoryLoader.getRestApiDriver();
        SGrRestApiDevice clemapDevice = new SGrRestApiDevice(deviceDesc, factory);
        return clemapDevice;
    }
}
