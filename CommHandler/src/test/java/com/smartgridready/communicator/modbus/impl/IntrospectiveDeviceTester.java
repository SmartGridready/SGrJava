package com.smartgridready.communicator.modbus.impl;

import com.smartgridready.ns.v0.DeviceFrame;

import com.smartgridready.communicator.common.helper.DeviceDescriptionLoader;
import com.smartgridready.communicator.common.helper.DriverFactoryLoader;
import com.smartgridready.communicator.common.impl.SGrDeviceBase;
import com.smartgridready.communicator.rest.http.client.ApacheHttpRequestFactory;
import com.smartgridready.communicator.rest.impl.SGrRestApiDevice;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;

import io.vavr.Tuple3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@SuppressWarnings({"DataFlowIssue","java:S2925"})
public class IntrospectiveDeviceTester {

    private static final Logger LOG = LoggerFactory.getLogger(GetValArrayTester.class);

    private static final String XML_BASE_DIR="../../SGrSpecifications/XMLInstances/ExtInterfaces/";

    private static final class ProtocolRecord {
        String fpName;
        String dpName;
        String readVal;
        String exception;

        @Override
        public String toString() {
            return String.format("%22s\t\t%18s\t\t%10s\t%s", fpName, dpName, readVal, exception);
        }
    }

    public static void main( String[] argv ) {

        try {
            Tuple3<DeviceFrame, SGrDeviceBase<?, ?, ?>, Properties> device;

            String useDevice = "CLEMAP";
            switch (useDevice) {
                case "SMART-ME": device = createSmartMeDevice(); break;
                case "CLEMAP": device = createClemapDevice(); break;
                case "SWISSPOWER": device = createSwisspowerDevice();break;
                case "GROUPE-E": device = createGroupeEDevice(); break;
                default: device = createWagoDevice();
            }

            DeviceFrame devDesc = device._1;
            SGrDeviceBase<?,?,?> deviceBase = device._2;
            Properties properties = device._3;

            // List containing data points to be read.
            final List<Tuple3<String, String, Properties>> datapPoints = new ArrayList<>();

            // Fill REST interface datapoints if any
            Optional.ofNullable(devDesc.getInterfaceList().getRestApiInterface()).ifPresent(restApiIf ->
                    restApiIf.getFunctionalProfileList().getFunctionalProfileListElement().forEach(fp-> {
                        String fpName = fp.getFunctionalProfile().getFunctionalProfileName();
                        fp.getDataPointList().getDataPointListElement().forEach( dp ->
                                datapPoints.add(new Tuple3<>(fpName, dp.getDataPoint().getDataPointName(), properties)));

            }));

            // Fill modbus interface datapoints if any
            Optional.ofNullable(devDesc.getInterfaceList().getModbusInterface()).ifPresent(modbusIf ->
                    modbusIf.getFunctionalProfileList().getFunctionalProfileListElement().forEach(fp -> {
                        String fpName = fp.getFunctionalProfile().getFunctionalProfileName();
                        fp.getDataPointList().getDataPointListElement().forEach( dp ->
                                datapPoints.add(new Tuple3<>(fpName, dp.getDataPoint().getDataPointName(), properties)));
                }
            ));
            
            System.out.println();

            final List<ProtocolRecord> protocol = new ArrayList<>();
            System.out.print("Introspective reading test: .");
            for (Tuple3<String, String, Properties> tuple : datapPoints) {
                protocol.add(checkDataPoint(tuple, deviceBase));
                Thread.sleep(100);
                System.out.print(".");
            }
            System.out.println();

            protocol.forEach(System.out::println);

        }
        catch ( Exception e )
        {
            LOG.error( "Error loading device description.", e);
        }
    }

    private static ProtocolRecord checkDataPoint(Tuple3<String,String, Properties> dataPoint, SGrDeviceBase<?, ?, ?> device) {

        ProtocolRecord protocolRecord = new ProtocolRecord();
        protocolRecord.readVal = "-";
        protocolRecord.exception = "";
        protocolRecord.fpName = dataPoint._1;
        protocolRecord.dpName = dataPoint._2;

        try {
            if (dataPoint._3 != null) {
                SGrRestApiDevice restApiDevice = (SGrRestApiDevice) device;
                protocolRecord.readVal = restApiDevice.getVal(dataPoint._1, dataPoint._2, dataPoint._3).getString();
            } else {
                protocolRecord.readVal = device.getVal(dataPoint._1, dataPoint._2).getString();
            }
        } catch (Exception e) {
            protocolRecord.exception = e.getMessage();
        }
        return protocolRecord;
    }

    private static Tuple3<DeviceFrame, SGrDeviceBase<?, ?, ?>, Properties> createWagoDevice() throws Exception {

        DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
        DeviceFrame devDesc = loader.load( XML_BASE_DIR, "SGr_04_0014_0000_WAGO_SmartMeterV0.2.1.xml");

        GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getImplementation(GenDriverAPI4ModbusFactory.class);
        GenDriverAPI4Modbus mbRTU = factory.createRtuTransport("COM3", 19200);
        mbRTU.connect();
        return new Tuple3<>(devDesc, new SGrModbusDevice(devDesc, mbRTU), null);
    }

    private static Tuple3<DeviceFrame, SGrDeviceBase<?, ?, ?>, Properties> createSmartMeDevice() throws Exception {

        Properties properties = new Properties();
        properties.put("username", "smith83@gmx.ch");
        properties.put("password", "lapo83");
        properties.put("device_id", "08fffe1c-f3ae-4afe-bd70-bc0a73d7ac31");

        DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
        DeviceFrame devDesc = loader.load( XML_BASE_DIR,
                "SGr_02_mmmm_8288089799_Smart-me_SubMeterElectricity_V1.0.0.xml",
                properties);

        SGrRestApiDevice restApiDevice = new SGrRestApiDevice(devDesc, new ApacheHttpRequestFactory());
        return new Tuple3<>(devDesc, restApiDevice, null);
    }

    private static Tuple3<DeviceFrame, SGrDeviceBase<?, ?, ?>, Properties> createClemapDevice() throws Exception {

        Properties properties = new Properties();
        properties.put("username", "hfurrer@ergonomics.ch");
        properties.put("password", "Holdrio99");
        properties.put("baseUri", "https://cloud.clemap.com:3032");
        properties.put("sensor_id", "63343431ecf2cf013a1e5a9f");

        DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
        DeviceFrame devDesc = loader.load( XML_BASE_DIR,
                "SGr_02_0018_CLEMAP_EIcloudEnergyMonitor_V1.0.0.xml",
                properties);

        SGrRestApiDevice restApiDevice = new SGrRestApiDevice(devDesc, new ApacheHttpRequestFactory());
        return new Tuple3<>(devDesc, restApiDevice, null);
    }

    private static Tuple3<DeviceFrame, SGrDeviceBase<?, ?, ?>, Properties> createSwisspowerDevice() throws Exception {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL deviceDescUrl = classloader.getResource("SGr_05_Swisspower_Dynamic_Tariffs_0.0.1.xml");

        DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
        DeviceFrame devDesc = loader.load("", deviceDescUrl != null ? deviceDescUrl.getPath() : null);

        SGrRestApiDevice restApiDevice = new SGrRestApiDevice(devDesc, new ApacheHttpRequestFactory());

        Properties parameters = new Properties();
        parameters.put("point", "CH1018601234500000000000000011642");
        parameters.put("start_timestamp", "2024-02-14T00:00:00+02:00");
        parameters.put("end_timestamp", "2024-02-16T02:00:00+02:00");
        return new Tuple3<>(devDesc, restApiDevice, parameters);
    }


    private static Tuple3<DeviceFrame, SGrDeviceBase<?, ?, ?>, Properties> createGroupeEDevice() throws Exception {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL deviceDescUrl = classloader.getResource("SGr_05_GroupeE_Dynamic_Tariffs_0.0.1.xml");

        DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
        DeviceFrame devDesc = loader.load("", deviceDescUrl != null ? deviceDescUrl.getPath() : null);

        SGrRestApiDevice restApiDevice = new SGrRestApiDevice(devDesc, new ApacheHttpRequestFactory());

        Properties parameters = new Properties();
        parameters.put("start_timestamp", "2023-09-06T00:00:00+02:00");
        parameters.put("end_timestamp", "2023-09-07T02:00:00+02:00");
        return new Tuple3<>(devDesc, restApiDevice, parameters);
    }

}
