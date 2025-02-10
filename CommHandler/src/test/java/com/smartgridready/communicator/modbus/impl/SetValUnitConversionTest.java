package com.smartgridready.communicator.modbus.impl;

import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.communicator.common.api.values.Float32Value;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.common.helper.DeviceDescriptionLoader;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SetValUnitConversionTest
{
    private final Logger LOG = LoggerFactory.getLogger(SetValUnitConversionTest.class);
    private final ArgumentCaptor<int[]> intArrayCaptor = ArgumentCaptor.forClass(int[].class);

    @Mock
    private GenDriverAPI4Modbus modbusDriver;

    @Test
    void getSetValWithConversion() throws Exception {

        SGrModbusDevice modbusDevice = createModbusDevice();

        int[] modbusRegisters = new int[]{0x0000435f, 0x000028f7}; // float 223.16
        float expectedValue = 223.16f * 1.36f;

        modbusDevice.setVal("VoltageAC", "VoltageL1", Float32Value.of(expectedValue));

        verify(modbusDriver).writeMultipleRegisters(anyShort(), anyInt(), intArrayCaptor.capture());
        assertArrayEquals(modbusRegisters, intArrayCaptor.getValue());

        when(modbusDriver.readHoldingRegisters(anyShort(), anyInt(), anyInt())).thenReturn(modbusRegisters);

        float resVal = createModbusDevice().getVal("VoltageAC", "VoltageL1").getFloat32();
        LOG.info("getSetValWithConversion: expectedVal={}, resVal={}", expectedValue, resVal);
        assertEquals(expectedValue, resVal);
    }

    @Test
    void getSetValWithScaling() throws Exception {

        SGrModbusDevice modbusDevice = createModbusDevice();

        int[] modbusRegisters = new int[]{0x0000450c, 0x0000a000}; // float32 2250
        float expectedValue = 22.5f;

        modbusDevice.setVal("ActiveEnergyAC", "ActiveEnergyACtot", Float32Value.of(expectedValue));

        verify(modbusDriver).writeMultipleRegisters(anyShort(), anyInt(), intArrayCaptor.capture());
        assertArrayEquals(modbusRegisters, intArrayCaptor.getValue());

        when(modbusDriver.readHoldingRegisters(anyShort(), anyInt(), anyInt())).thenReturn(modbusRegisters);

        float resVal = createModbusDevice().getVal("ActiveEnergyAC", "ActiveEnergyACtot").getFloat32();
        LOG.info("getSetValWithScaling: expectedVal={}, resVal={}", expectedValue, resVal);
        assertEquals(expectedValue, resVal);
    }

    @Test
    void setGetValWithLayer6Deviation_H2L() throws Exception {

        SGrModbusDevice modbusDevice = createModbusDevice();

        int[] modbusRegisters = new int[]{0x00000024, 0x00000a4}; // int H2L 36kW 164W
        int expectedValue = 36164;

        when(modbusDriver.readHoldingRegisters(anyShort(), anyInt(), anyInt())).thenReturn(modbusRegisters);

        Value resVal = modbusDevice.getVal("ActiveEnergyAC", "ActiveEnergyACL1");
        LOG.info("setGetValWithLayer6Deviation_H2L: expectedVal={}, resVal={}", expectedValue, resVal.getInt32());
        assertEquals(expectedValue, resVal.getInt32());
    }

    @Test
    void setGetValWithLayer6Deviation_L2H() throws Exception {

        SGrModbusDevice modbusDevice = createModbusDevice();

        int[] modbusRegisters = new int[]{0x000000a4, 0x0000024}; // int H2L 36kW 164W
        int expectedValue = 36164;

        when(modbusDriver.readHoldingRegisters(anyShort(), anyInt(), anyInt())).thenReturn(modbusRegisters);

        Value resVal = modbusDevice.getVal("ActiveEnergyAC", "ActiveEnergyACL2");
        LOG.info("setGetValWithLayer6Deviation_L2H: expectedVal={}, resVal={}", expectedValue, resVal.getInt32());
        assertEquals(expectedValue, resVal.getInt32());
    }


    private  SGrModbusDevice createModbusDevice() {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL deviceDescUrl = classloader.getResource("SGr_04_0014_0000_WAGO_SmartMeterV0.2.1-GenericAttributes.xml");
        DeviceFrame deviceDesc = new DeviceDescriptionLoader()
                .load("", Optional.ofNullable(deviceDescUrl != null ? deviceDescUrl.getPath() : null).orElse(""));

        return new SGrModbusDevice(deviceDesc, modbusDriver);
    }

}
