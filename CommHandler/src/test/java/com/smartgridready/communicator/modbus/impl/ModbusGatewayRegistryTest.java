package com.smartgridready.communicator.modbus.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import java.util.Optional;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.smartgridready.ns.v0.DeviceFrame;

import com.smartgridready.communicator.common.helper.DeviceDescriptionLoader;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;
import com.smartgridready.communicator.modbus.api.GenDeviceApi4Modbus;
import com.smartgridready.communicator.modbus.api.ModbusGatewayRegistry;

class ModbusGatewayRegistryTest {

    private GenDriverAPI4ModbusFactory modbusDriverFactory;
    private ModbusGatewayRegistry modbusGatewayRegistry;

    @BeforeEach
    void setUp() {
        modbusDriverFactory = new MockModbusDriverFactory();
        modbusGatewayRegistry = new SGrModbusGatewayRegistry();
    }

    @Test
    void testSingleRtuDevice() throws GenDriverException {
        assertEquals(0, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        GenDeviceApi4Modbus device = createModbusRtuDevice("COM3");
        assertEquals(1, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        device.connect();
        device.disconnect();
        device = null;
        modbusGatewayRegistry.detachAllGateways();

        assertEquals(0, modbusGatewayRegistry.getAllGatewayIdentifiers().size());
    }

    @Test
    void testSingleTcpDevice() throws GenDriverException {
        assertEquals(0, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        GenDeviceApi4Modbus device = createModbusTcpDevice();
        assertEquals(0, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        device.connect();
        device.disconnect();
        device = null;

        modbusGatewayRegistry.detachAllGateways();

        assertEquals(0, modbusGatewayRegistry.getAllGatewayIdentifiers().size());
    }

    @Test
    void testMultipleDevicesSameGateway() throws GenDriverException {
        assertEquals(0, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        GenDeviceApi4Modbus device1 = createModbusRtuDevice("COM3");
        assertEquals(1, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        GenDeviceApi4Modbus device2 = createModbusRtuDevice("COM3");
        assertEquals(1, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        device1.connect();
        device2.connect();

        device1.disconnect();
        device1 = null;
        assertEquals(1, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        device2.disconnect();
        device2 = null;
        assertEquals(1, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        modbusGatewayRegistry.detachAllGateways();

        assertEquals(0, modbusGatewayRegistry.getAllGatewayIdentifiers().size());
    }

    @Test
    void testMultipleDevicesDifferentGateways() throws GenDriverException {
        assertEquals(0, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        GenDeviceApi4Modbus device1 = createModbusRtuDevice("COM3");
        assertEquals(1, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        GenDeviceApi4Modbus device2 = createModbusRtuDevice("COM4");
        assertEquals(2, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        device1.connect();
        device2.connect();

        device1.disconnect();
        device1 = null;
        assertEquals(2, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        device2.disconnect();
        device2 = null;
        assertEquals(2, modbusGatewayRegistry.getAllGatewayIdentifiers().size());

        modbusGatewayRegistry.detachAllGateways();

        assertEquals(0, modbusGatewayRegistry.getAllGatewayIdentifiers().size());
    }

    private  SGrModbusDevice createModbusTcpDevice() throws GenDriverException {

        Properties properties = new Properties();
        properties.put("ipaddress", "127.0.0.1");

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL deviceDescUrl = classloader.getResource("SGr_04_0014_0000_WAGO_Testsystem_ModbusTCP_V1.0.xml");
        DeviceFrame deviceDesc = new DeviceDescriptionLoader()
                .load("", Optional.ofNullable(deviceDescUrl != null ? deviceDescUrl.getPath() : null).orElse(""), properties);

        return new SGrModbusDevice(deviceDesc, modbusDriverFactory, modbusGatewayRegistry);
    }

    private  SGrModbusDevice createModbusRtuDevice(String portName) throws GenDriverException {

        Properties properties = new Properties();
        properties.put("portName", portName);

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL deviceDescUrl = classloader.getResource("SGr_04_0014_0000_WAGO_SmartMeterV0.2.1-GenericAttributes.xml");
        DeviceFrame deviceDesc = new DeviceDescriptionLoader()
                .load("", Optional.ofNullable(deviceDescUrl != null ? deviceDescUrl.getPath() : null).orElse(""), properties);

        return new SGrModbusDevice(deviceDesc, modbusDriverFactory, modbusGatewayRegistry);
    }
}
