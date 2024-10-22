package com.smartgridready.communicator.modbus.integrationtest;

import com.smartgridready.communicator.common.api.values.DataType;
import com.smartgridready.ns.v0.DataPointDescription;
import com.smartgridready.ns.v0.DataTypeProduct;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.ModbusDataPointConfiguration;
import com.smartgridready.ns.v0.ModbusDataType;
import com.smartgridready.ns.v0.ModbusInterface;
import com.smartgridready.ns.v0.RegisterType;
import com.smartgridready.communicator.common.helper.DeviceDescriptionLoader;
import com.smartgridready.communicator.common.helper.DriverFactoryLoader;
import com.smartgridready.communicator.common.impl.SGrDeviceBase;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;
import com.smartgridready.communicator.modbus.impl.SGrModbusDevice;
import io.vavr.Tuple2;
import io.vavr.Tuple3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class TestDevice {

    private SGrDeviceBase<?, ?, ?> testSystem;

    private DeviceFrame deviceDescriptor;

    public static class DataPointDescriptor {

        public String functionalProfile;
        public String dataPoint;
        public boolean isReadable;
        public boolean isWritable;
        public RegisterType modbusRegisterType;
        public DataTypeProduct genericType;
        public ModbusDataType modbusType;
        public String modbusAddress;
        public String modbusNbOfRegisters;

        public String minVal;

        public String  maxVal;

        public String conversionFactor;

        public String units;
        @Override
        public String toString() {
            return "FP=" + functionalProfile +
                    " DP=" + dataPoint + " "
                    + (isReadable ? "R" : "") + " "
                    + (isWritable ? "W" : "") + " "
                    + modbusRegisterType.name()
                    + " GenType=" + DataType.getGenDataTypeName(genericType)
                    + " ModbusType=" + DataType.getModbusDataTypeName(modbusType)
                    + " ModbusAddress=" + modbusAddress
                    + " ModbusRegisterType=" + modbusRegisterType
                    + " ModbusNoOfRegisters=" + modbusNbOfRegisters
                    + " Units=" + units
                    + " MinValue=" + minVal
                    + " MaxValue=" + maxVal
                    + " UnitConversionFactor=" + conversionFactor;
        }
    }

    protected void init() throws Exception {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL propertyUrl = classloader.getResource("wago-testsystem.properties");
        if (propertyUrl==null) {
            throw new IOException("Resource file 'wago-testsystem.properties' not found.");
        }

        Properties properties = new Properties();
        properties.load(new FileInputStream(propertyUrl.getPath()));
        String testsystemIp = properties.getProperty("wagosystem.ip");
        String testsystemPort = properties.getProperty("wagosystem.port");
        String deviceDescriptionFile = properties.getProperty("ei-xml.filename");

        URL deviceDescriptionUrl = classloader.getResource(deviceDescriptionFile);
        if (deviceDescriptionUrl==null) {
            throw new IOException(String.format("Resource file %s not found.", deviceDescriptionFile));
        }

        DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
        deviceDescriptor = loader.load("", deviceDescriptionUrl.getPath());

        GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getImplementation(GenDriverAPI4ModbusFactory.class);
        GenDriverAPI4Modbus driver = factory.createTcpTransport(testsystemIp, Integer.parseInt(testsystemPort));
        driver.connect();
        driver.setUnitIdentifier((short) 1);

        testSystem = new SGrModbusDevice(deviceDescriptor, driver);
    }

    public void loadDeviceDescriptionFile(
            File file,
            Function<String, Tuple2<String, Integer>> getIpConnParams,
            Function<String, Tuple3<String, Integer, Integer>> getComPortConnParams ) throws Exception {

        String filePath = file.getPath().replace("\\", "/");
        deviceDescriptor = new DeviceDescriptionLoader().load("", filePath);

        ModbusInterface modbusInterface = deviceDescriptor.getInterfaceList().getModbusInterface();
        if (modbusInterface != null && modbusInterface.getModbusInterfaceDescription().getModbusTcp() != null) {
            Tuple2<String, Integer> connParams = getIpConnParams.apply("Enter IP Address and Port");
            GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getImplementation(GenDriverAPI4ModbusFactory.class);
            GenDriverAPI4Modbus driver = factory.createTcpTransport(connParams._1, connParams._2);
            driver.connect();
            testSystem = new SGrModbusDevice(deviceDescriptor,driver );
        }
        if (modbusInterface != null && modbusInterface.getModbusInterfaceDescription().getModbusRtu() != null) {
            Tuple3<String, Integer, Integer> connParams = getComPortConnParams.apply("Enter COMx port, baud rate and modbus identifier.");
            GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getImplementation(GenDriverAPI4ModbusFactory.class);
            GenDriverAPI4Modbus driver = factory.createRtuTransport(connParams._1, connParams._2);
            driver.connect();
            driver.setUnitIdentifier(connParams._3.shortValue());
            testSystem = new SGrModbusDevice(deviceDescriptor, driver);
        }

        if (deviceDescriptor.getInterfaceList().getRestApiInterface() != null ) {
           throw new IllegalArgumentException("Rest API devices are not supported yet.");
        }

        if (deviceDescriptor.getInterfaceList().getMessagingInterface() != null ) {
            throw new IllegalArgumentException("Messaging devices are not supported yet.");
        }

    }

    public SGrDeviceBase<?, ?, ?> getTestSystem() {
        return testSystem;
    }

    public List<DataPointDescriptor> getDataPoints() {

        List<DataPointDescriptor> dataPoints = new ArrayList<>();

        deviceDescriptor.getInterfaceList().getModbusInterface().getFunctionalProfileList()
                .getFunctionalProfileListElement().forEach(fp -> {
                    String fpName = fp.getFunctionalProfile().getFunctionalProfileName();
                    fp.getDataPointList().getDataPointListElement().forEach(dataPoint -> {
                            DataPointDescriptor dpDesc = new DataPointDescriptor();
                            dpDesc.functionalProfile = fpName;

                            DataPointDescription dp = dataPoint.getDataPoint();
                            dpDesc.dataPoint = dp.getDataPointName();
                            dpDesc.isReadable = dp.getDataDirection().value().contains("R");
                            dpDesc.isWritable = dp.getDataDirection().value().contains("W");
                            dpDesc.genericType = dp.getDataType();
                            dpDesc.units = dp.getUnit().name();
                            dpDesc.minVal = dp.getMinimumValue() != null ? String.valueOf(dp.getMinimumValue()) : "n.a";
                            dpDesc.maxVal = dp.getMaximumValue() != null ? String.valueOf(dp.getMaximumValue()) : "n.a";
                            dpDesc.conversionFactor = dp.getUnitConversionMultiplicator() != null ? String.valueOf(dp.getUnitConversionMultiplicator()) : "n.a";

                            ModbusDataPointConfiguration modbusDp = dataPoint.getModbusDataPointConfiguration();
                            dpDesc.modbusRegisterType = modbusDp.getRegisterType();
                            dpDesc.modbusType = modbusDp.getModbusDataType();
                            dpDesc.modbusAddress = modbusDp.getAddress().toString();
                            dpDesc.modbusNbOfRegisters = String.valueOf(modbusDp.getNumberOfRegisters());
                            dataPoints.add(dpDesc);
                    });
                });

        return dataPoints;
    }
}
