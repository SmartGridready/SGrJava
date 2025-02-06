package com.smartgridready.communicator.common.impl;

import com.smartgridready.communicator.common.api.values.ArrayValue;
import com.smartgridready.communicator.common.api.values.DataType;
import com.smartgridready.communicator.common.api.values.DataTypeInfo;
import com.smartgridready.communicator.common.api.values.Float32Value;
import com.smartgridready.driver.api.http.GenHttpRequest;
import com.smartgridready.driver.api.http.GenHttpClientFactory;
import com.smartgridready.driver.api.http.GenHttpResponse;
import com.smartgridready.driver.api.http.GenUriBuilder;
import com.smartgridready.driver.api.http.HttpMethod;
import com.smartgridready.ns.v0.DataDirectionProduct;
import com.smartgridready.ns.v0.DataTypeProduct;
import com.smartgridready.ns.v0.DeviceCategory;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.EmptyType;
import com.smartgridready.ns.v0.FunctionalProfileCategory;
import com.smartgridready.ns.v0.Language;
import com.smartgridready.ns.v0.ModbusDataType;
import com.smartgridready.ns.v0.Units;
import com.smartgridready.communicator.common.api.dto.ConfigurationValue;
import com.smartgridready.communicator.common.api.dto.DataPoint;
import com.smartgridready.communicator.common.api.dto.FunctionalProfile;
import com.smartgridready.communicator.common.api.dto.GenericAttribute;
import com.smartgridready.communicator.common.api.dto.InterfaceType;
import com.smartgridready.communicator.common.api.dto.OperationEnvironment;
import com.smartgridready.communicator.common.api.values.EnumValue;
import com.smartgridready.communicator.common.api.values.Float64Value;
import com.smartgridready.communicator.common.api.values.Int64Value;
import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.common.helper.DeviceDescriptionLoader;
import com.smartgridready.communicator.common.impl.SGrDeviceBase.Comparator;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.communicator.modbus.impl.SGrModbusDevice;
import com.smartgridready.communicator.rest.impl.SGrRestApiDevice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

import static com.smartgridready.communicator.common.api.values.DataType.ENUM;
import static com.smartgridready.communicator.common.api.values.DataType.UNKNOWN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
class SGrDeviceBaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(SGrDeviceBase.class);

    private static final Value INTEGER_VALUE_20482048 = Int64Value.of(20482048L);
    private static final Value DOUBLE_VALUE_4096_00009999 = Float64Value.of(4096.00009999d);

    private static final String CLEMAP_METER_RESP = "[{\"sensor_id\":\"63343431ecf2cf013a1e5a9f\",\"opm\":{\"value\":1,\"last_updt\":\"2022-11-07T15:19:54.560Z\"},\"ten_sec\":{\"p_l1\":1.5,\"p_l2\":2,\"p_l3\":4.0,\"q_l1\":2.3,\"q_l2\":0,\"q_l3\":4,\"last_upd\":\"2022-11-07T15:21:50.000Z\"},\"one_min\":{\"p_l1\":0.003,\"p_l2\":0,\"p_l3\":0,\"q_l1\":-0.002,\"q_l2\":0,\"q_l3\":0,\"avg_energy_l1\":0,\"avg_energy_l2\":0,\"avg_energy_l3\":0,\"v_l1\":227.869,\"v_l2\":0,\"v_l3\":0,\"i_l1\":0.001,\"i_l2\":0,\"i_l3\":0,\"s_l1\":0.173,\"s_l2\":0,\"s_l3\":0,\"pf_l1\":0.085,\"pf_l2\":1,\"pf_l3\":1,\"last_update\":\"2022-11-07T15:19:47.579Z\"}}]";

    private static final String CLEMAP_AUTH_RESP = "{\r\n"
            + "    \"accessToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6ImFjY2VzcyJ9.eyJ1c2VySWQiOiI2MzM0MzI5MWVjZjJjZjAxM2ExZTVhOWQiLCJpYXQiOjE2NjgwOTI1NzMsImV4cCI6MTY2ODE3ODk3MywiYXVkIjoiaHR0cHM6Ly95b3VyZG9tYWluLmNvbSIsImlzcyI6ImZlYXRoZXJzIiwic3ViIjoiNjMzNDMyOTFlY2YyY2YwMTNhMWU1YTlkIiwianRpIjoiNTU2NmU2M2QtNTVmOC00MDEyLWJlYTUtOTJjYWE0NDIwYzQ3In0.xVOrspfi1E61Jb0BXBt37wgqGcnkPueMcHh1_zI-xXM\",\r\n"
            + "    \"user\": {\r\n"
            + "        \"_id\": \"63343291ecf2cf013a1e5a9d\",\r\n"
            + "        \"email\": \"hfurrer@ergonomics.ch\",\r\n"
            + "        \"groups\": [\r\n"
            + "            \"owner_smartgrid\",\r\n"
            + "            \"owner_smartfactory\"\r\n"
            + "        ],\r\n"
            + "        \"roles\": [\r\n"
            + "            \"default\"\r\n"
            + "        ]\r\n"
            + "    }\r\n"
            + "}";
    
    private static final URI TEST_URI = URI.create("");

    private static final Value STRING_VALUE_1000 = StringValue.of("1000");

    enum Expect {
        OK,
        ERROR
    }

    @Mock
    private GenDriverAPI4ModbusFactory modbusClientFactory;

    @Mock
    private GenDriverAPI4Modbus genDriverAPI4Modbus;

    @Mock
    private GenHttpRequest httpRequest;

    @Mock
    private GenUriBuilder uriBuilder;

    @Mock
    private GenHttpClientFactory httpClientFactory;

    private final ArgumentCaptor<Integer> intCaptor1 = ArgumentCaptor.forClass(Integer.class);
    private final ArgumentCaptor<int[]> intCaptor2 = ArgumentCaptor.forClass(int[].class);

    private static Stream<Arguments> checkRangeArguments() {
        return Stream.of(
                Arguments.of(new Value[]{INTEGER_VALUE_20482048}, Comparator.MIN, 20482048d, Expect.OK),
                Arguments.of(new Value[]{INTEGER_VALUE_20482048}, Comparator.MIN, 20482049d, Expect.ERROR),
                Arguments.of(new Value[]{INTEGER_VALUE_20482048}, Comparator.MAX, 20482048d, Expect.OK),
                Arguments.of(new Value[]{INTEGER_VALUE_20482048}, Comparator.MAX, 20482047d, Expect.ERROR),

                Arguments.of(new Value[]{DOUBLE_VALUE_4096_00009999}, Comparator.MIN, 4096.00009999d, Expect.OK),
                Arguments.of(new Value[]{DOUBLE_VALUE_4096_00009999}, Comparator.MIN, 4096.00010000d, Expect.ERROR),
                Arguments.of(new Value[]{DOUBLE_VALUE_4096_00009999}, Comparator.MAX, 4096.00009999d, Expect.OK),
                Arguments.of(new Value[]{DOUBLE_VALUE_4096_00009999}, Comparator.MAX, 4096.00009998d, Expect.ERROR),

                Arguments.of(new Value[]{STRING_VALUE_1000}, Comparator.MIN, 1000d, Expect.OK),
                Arguments.of(new Value[]{STRING_VALUE_1000}, Comparator.MIN, 1001d, Expect.ERROR),
                Arguments.of(new Value[]{STRING_VALUE_1000}, Comparator.MAX, 1000d, Expect.OK),
                Arguments.of(new Value[]{STRING_VALUE_1000}, Comparator.MAX, 999d,  Expect.ERROR)
        );
    }

    @ParameterizedTest
    @MethodSource("checkRangeArguments")
    void checkRange(Value[] values, Comparator comparator, double limit, Expect expect) throws Exception {

        SGrModbusDevice device = createSGrModbusDevice("SGr_04_0014_0000_WAGO_SmartMeterV0.2.1-GenericAttributes.xml");

        if (expect.equals(Expect.ERROR)) {
            assertThrows(GenDriverException.class, () -> device.checkOutOfRange(values, limit, comparator));
        } else {
            assertDoesNotThrow(() -> device.checkOutOfRange(values, limit, comparator));
        }
    }

    @Test
    void getDeviceInfo() throws Exception {

        var device = createSGrModbusDevice("SGr_04_0014_0000_WAGO_SmartMeterV0.2.1-GenericAttributes.xml");
        var deviceInfo = device.getDeviceInfo();

        assertEquals("WAGOMeterV0.2.1", deviceInfo.getName());
        assertEquals(DeviceCategory.SUB_METER_ELECTRICITY, deviceInfo.getDeviceCategory());
        assertEquals(InterfaceType.MODBUS, deviceInfo.getInterfaceType());
        assertEquals("1.0.0", deviceInfo.getHardwareVersion());
        assertEquals("1.0.0", deviceInfo.getSoftwareVersion());
        assertEquals(OperationEnvironment.LOCAL, deviceInfo.getOperationEnvironment());
        assertEquals("WAGO", deviceInfo.getManufacturer());

        List<GenericAttribute> expectedAttributes = new ArrayList<>();
        expectedAttributes.add(createGenericAttributeProduct(
                "SpecialQualityRequirement",
                "METAS",
                DataType.STRING,
                Units.NONE,
                new ArrayList<>()));

        expectedAttributes.add(createGenericAttributeProduct(
                "Curtailment",
                "40.5f",
                DataType.FLOAT32,
                Units.PERCENT,
                new ArrayList<>()));

        var assistsAttr = createGenericAttributeProduct("assists", "TNO", DataType.STRING, Units.NONE, null);
        var obligedToAttr = createGenericAttributeProduct("obligedTo", "SHALL", DataType.STRING, Units.NONE, null);
        List<GenericAttribute> children = new ArrayList<>();
        children.add(assistsAttr);
        children.add(obligedToAttr);

        expectedAttributes.add(createGenericAttributeProduct(
                "FlexDirManagement",
                null,
                null,
                null,
                children));

        List<GenericAttribute> deviceGenericAttributes = deviceInfo.getGenericAttributes();
        assertArrayEquals(expectedAttributes.toArray(), deviceGenericAttributes.toArray());

        for (var attribute : deviceGenericAttributes) {
            LOG.info(attribute.toString());
        }

        checkFunctionalProfiles(deviceInfo.getFunctionalProfiles());

    }


    @Test
    void getDeviceInfoDeviceData() throws Exception {

        var device = createSGrModbusDevice("SGr_04_0014_0000_WAGO_SmartMeterV0.2.1-GenericAttributes.xml");
        var deviceInfo = device.getDeviceInfo();

        assertEquals( "WAGOMeterV0.2.1", deviceInfo.getName());
        assertEquals("WAGO", deviceInfo.getManufacturer());
        assertEquals(DeviceCategory.SUB_METER_ELECTRICITY, deviceInfo.getDeviceCategory());
        assertEquals( "0.2.3", deviceInfo.getVersionNumber());
        assertEquals( "1.0.0", deviceInfo.getHardwareVersion());
        assertEquals( "1.0.0", deviceInfo.getSoftwareVersion());
        assertEquals( InterfaceType.MODBUS, deviceInfo.getInterfaceType());
        assertEquals( OperationEnvironment.LOCAL, deviceInfo.getOperationEnvironment());
    }

    @Test
    void getFunctionalProfiles() throws Exception {

        var device = createSGrModbusDevice("SGr_04_0014_0000_WAGO_SmartMeterV0.2.1-GenericAttributes.xml");
        var functionalProfiles = device.getFunctionalProfiles();

        checkFunctionalProfiles(functionalProfiles);
    }

    @Test
    void getFunctionalProfile() throws Exception {
        var device = createSGrModbusDevice("SGr_04_0014_0000_WAGO_SmartMeterV0.2.1-GenericAttributes.xml");
        var functionalProfile = device.getFunctionalProfile("VoltageAC");
        checkFunctionalProfileDetailsVoltageAC(functionalProfile);
    }

    @Test
    void readWriteValueUsingModbusDataPoint() throws Exception {

        final float voltage = 230.2f;
        final float unitConversionFactor = 1.36f;

        var modbusDataType = new ModbusDataType();
        modbusDataType.setFloat32(new EmptyType());

        var modbusValue = Float32Value.of(voltage).toModbusRegister(modbusDataType);

        when(modbusClientFactory.createRtuTransport(anyString(), anyInt(), any(), any(), any())).thenReturn(genDriverAPI4Modbus);
        when(genDriverAPI4Modbus.readHoldingRegisters(anyShort(), anyInt(), anyInt()))
                .thenReturn(modbusValue);

        var device = createSGrModbusDevice("SGr_04_0014_0000_WAGO_SmartMeterV0.2.1-GenericAttributes.xml");
        var dataPoint = device.getDataPoint("VoltageAC", "VoltageL1");

        // Check all metadata
        checkVoltageL1DataPoint(dataPoint);

        // Check read value
        var value = dataPoint.getVal();
        assertEquals(String.format("%.4f", unitConversionFactor * voltage), String.format("%.4f", value.getFloat32()));

        // Check write value
        value = Float32Value.of(unitConversionFactor * voltage);
        dataPoint.setVal(value);

        verify(genDriverAPI4Modbus).writeMultipleRegisters(anyShort(), intCaptor1.capture(), intCaptor2.capture());
        assertEquals(20482, intCaptor1.getValue());   // Address
        assertArrayEquals(modbusValue, intCaptor2.getValue()); // float value on modbus
    }

    @Test
    void getEnumDataPoint() throws Exception {
        var device = createSGrModbusDevice("SGr_04_0014_0000_WAGO_Testsystem_V1.0.xml");
        var dataPoint = device.getDataPoint("DigitalRegister_M2_OUT_Enum", "Register");

        List<EnumValue> expected = new ArrayList<>();
        expected.add(EnumValue.of("ENUM_00", 0L, "Register bits 00"));
        expected.add(EnumValue.of("ENUM_01", 1L, "Register bits 01"));
        expected.add(EnumValue.of("ENUM_10", 2L, "Register bits 10"));
        expected.add(EnumValue.of("ENUM_11", 3L, "Register bits 11"));

        assertEquals(DataType.ENUM, dataPoint.getDataType().getType());
        assertArrayEquals(expected.toArray(), dataPoint.getDataType().getRange().toArray());
    }

    @Test
    void getEnumDataPoints() throws Exception {

        // Fixtures
        var hpOpModeCmdEnums = new String[]{
                "STI_WP_EMERG_OP", "STI_WP_READY", "STI_WP_PROG_OP",
                "STI_WP_COMFORT_OP", "STI_WP_ECO_OP", "STI_WP_DOM_WATER_OP"};

        var sgReadyStateAndOpModeCmds = new String[] {"HP_LOCKED", "HP_NORMAL", "HP_INTENSIFIED", "HP_FORCED" };

        var expectedDatapointEnumsMap = new HashMap<String, String[]>();
        expectedDatapointEnumsMap.put("HPOpModeCmd", hpOpModeCmdEnums);
        expectedDatapointEnumsMap.put("SGReadyOpModeCmd", sgReadyStateAndOpModeCmds);
        expectedDatapointEnumsMap.put("SGReadyState", sgReadyStateAndOpModeCmds);

        var device = createSGrModbusDevice("SGr_04_0015_xxxx_StiebelEltron_HeatPump_V1.0.0.xml");

        var enumResults = new HashMap<String, String[]>();
        device.getFunctionalProfiles().forEach(
                fp -> fp.getDataPoints().forEach(
                    dp -> {
                        DataTypeInfo dt = dp.getDataType();
                        if (dt.getType() == ENUM && dt.getRange() != null) {
                            enumResults.put(dp.getName(), dt.getRange().stream().map(Value::getString).toArray(String[]::new));
                        }
                    }));

        assertArrayEquals(expectedDatapointEnumsMap.get("HPOpModeCmd"), enumResults.get("HPOpModeCmd"));
        assertArrayEquals(expectedDatapointEnumsMap.get("SGReadyOpModeCmd"), enumResults.get("SGReadyOpModeCmd"));
        assertArrayEquals(expectedDatapointEnumsMap.get("SGReadyState"), enumResults.get("SGReadyState"));
    }

    @Test
    void getBitmapDataPoint() throws Exception {

        var device = createSGrModbusDevice("SGr_04_0014_0000_WAGO_Testsystem_V1.0.xml");
        var dataPoint = device.getDataPoint("DigitalRegister_M1_IN_1", "Register");

        List<Value> expected = new ArrayList<>();
        expected.add(StringValue.of("Sensor_1"));
        expected.add(StringValue.of("Sensor_2"));
        expected.add(StringValue.of("Sensor_3"));
        expected.add(StringValue.of("Sensor_4"));
        expected.add(StringValue.of("Sensor_5"));
        expected.add(StringValue.of("Sensor_6"));
        expected.add(StringValue.of("Sensor_7"));
        expected.add(StringValue.of("Sensor_8"));

        assertEquals(DataType.BITMAP, dataPoint.getDataType().getType());
        assertIterableEquals(expected, dataPoint.getDataType().getRange());
    }

    @Test
    void readWriteArrayUsingModbusDataPoint() throws Exception {

        final float voltage = 230.2f;

        var modbusDataType = new ModbusDataType();
        modbusDataType.setFloat32(new EmptyType());

        var modbusValue = Float32Value.of(voltage).toModbusRegister(modbusDataType);
        var modbusValues = new int[3*modbusValue.length];
        System.arraycopy(modbusValue, 0, modbusValues, 0, modbusValue.length);
        System.arraycopy(modbusValue, 0, modbusValues, modbusValue.length, modbusValue.length);
        System.arraycopy(modbusValue, 0, modbusValues, 2*modbusValue.length,modbusValue.length);

        when(modbusClientFactory.createRtuTransport(anyString(), anyInt(), any(), any(), any())).thenReturn(genDriverAPI4Modbus);
        when(genDriverAPI4Modbus.readHoldingRegisters(anyShort(), anyInt(), anyInt()))
                .thenReturn(modbusValues);

        var device = createSGrModbusDevice("SGr_04_0014_0000_WAGO_SmartMeterV0.2.1-Arrays.xml");
        var dataPoint = device.getDataPoint("VoltageAC", "Voltage-L1-L2-L3");

        // Check the datapoint's array length
        assertEquals(3, dataPoint.getArrayLen());

        // Check read value
        var values = dataPoint.getVal().asArray();
        for (int i=0; i<3; i++) {
            assertEquals(String.format("%.4f", voltage), String.format("%.4f", values[i].getFloat32()));
        }

        // Check write value
        dataPoint.setVal(ArrayValue.of(values));

        verify(genDriverAPI4Modbus).writeMultipleRegisters(anyShort(), intCaptor1.capture(), intCaptor2.capture());
        assertEquals(20482, intCaptor1.getValue());   // Address
        assertArrayEquals(modbusValues, intCaptor2.getValue()); // float value on modbus
    }

    @Test
    void readFromRestApiDataPoint() throws Exception {

        when(httpClientFactory.createHttpRequest()).thenReturn(httpRequest);
        when(httpClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addPath(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addQueryParameter(any(), any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.setQueryString(any())).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(TEST_URI);

        var device = createSGrRestApiDevice("SGr_04_0018_CLEMAP_EIcloudEnergyMonitorV0.2.1.xml");
        var dataPoint = device.getDataPoint("ActivePowerAC", "ActivePowerACtot");

        when(httpRequest.execute()).thenReturn(GenHttpResponse.of(CLEMAP_AUTH_RESP));
        device.connect();
        verify(httpRequest).setUri(any(URI.class));
        verify(httpRequest).setHttpMethod(HttpMethod.POST);
        verify(httpRequest).setBody(anyString());
        verify(httpRequest).execute();

        when(httpRequest.execute()).thenReturn(GenHttpResponse.of(CLEMAP_METER_RESP));
        var value = dataPoint.getVal();
        assertEquals(0.0075000003f, value.getFloat32());
    }

    @Test
    void writeToRestApiDataPoint() throws Exception {

        when(httpClientFactory.createHttpRequest()).thenReturn(httpRequest);
        when(httpClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addPath(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addQueryParameter(any(), any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.setQueryString(any())).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(TEST_URI);

        var device = createSGrRestApiDevice("SGr_04_0018_CLEMAP_EIcloudEnergyMonitorV0.2.1.xml");
        var dataPoint = device.getDataPoint("ActivePowerAC", "ActivePowerACtot");

        when(httpRequest.execute()).thenReturn(GenHttpResponse.of(CLEMAP_AUTH_RESP));
        device.connect();
        verify(httpRequest).execute();

        Mockito.reset(httpRequest);

        when(httpRequest.execute()).thenReturn(GenHttpResponse.of(""));
        dataPoint.setVal(Float32Value.of(0.05f));
        verify(httpRequest).setUri(any(URI.class));
        verify(httpRequest).setHttpMethod(HttpMethod.POST);
        verify(httpRequest).setBody("{ \"value\": 49.99999837018557 }");
        verify(httpRequest).execute();
    }

    @Test
    void getConfiguration() throws Exception {
        var device = createSGrRestApiDevice("SGr_04_0018_CLEMAP_EIcloudEnergyMonitorV0.2.1.xml");
        var configuration = device.getDeviceConfigurationInfo();
        checkBaseUriConfigList(configuration);
    }

    @Test
    void getDeviceInfoWithConfiguration() throws Exception {
        var device = createSGrRestApiDevice("SGr_04_0018_CLEMAP_EIcloudEnergyMonitorV0.2.1.xml");
        var deviceInfo = device.getDeviceInfo();
        var configurationInfo = deviceInfo.getConfigurationInfo();
        checkBaseUriConfigList(configurationInfo);
    }

    private void checkVoltageACProfile(FunctionalProfile profile) {
        // check data points
        Set<String> expectedDataPointNames = new HashSet<>();
        expectedDataPointNames.add("VoltageL1");
        expectedDataPointNames.add("VoltageL2");
        expectedDataPointNames.add("VoltageL3");
        expectedDataPointNames.add("VoltageACL1-L2");
        expectedDataPointNames.add("VoltageACL1-L3");
        expectedDataPointNames.add("VoltageACL2-L3");
        profile.getDataPoints().forEach(dataPoint -> {
            if (dataPoint.getName().equals("VoltageL1")) {
                checkFunctionalProfileDetailsVoltageAC(profile);
                checkVoltageL1DataPoint(dataPoint);
            }
            expectedDataPointNames.remove(dataPoint.getName());
        });
        assertTrue(expectedDataPointNames.isEmpty());

        checkVoltageACProfileGenericAttributes(profile);
    }

    private void checkVoltageACProfileGenericAttributes(FunctionalProfile profile) {

       var genericAttributes = profile.getGenericAttributes();

       assertEquals(1, genericAttributes.size());

       var genAttr = genericAttributes.stream().findFirst().orElseGet(() -> fail("Functional Profile's GenericAttribute is missing"));
       assertEquals("LoadReduction", genAttr.getName());
       assertEquals(DataType.FLOAT32, genAttr.getDataType().getType());
       assertEquals(Units.PERCENT, genAttr.getUnit());
       assertEquals(20.5, genAttr.getValue().getFloat32());
    }

    private void checkFunctionalProfiles(List<FunctionalProfile> functionalProfiles) {
        Set<String> expectedProfileNames = new HashSet<>();
        expectedProfileNames.add("VoltageAC");
        expectedProfileNames.add("Frequency");
        expectedProfileNames.add("CurrentAC");
        expectedProfileNames.add("ActivePowerAC");
        expectedProfileNames.add("ReactivePowerAC");
        expectedProfileNames.add("ApparentPowerAC");
        expectedProfileNames.add("PowerFactor");
        expectedProfileNames.add("ActiveEnergyAC");
        expectedProfileNames.add("ReactiveEnergyAC");
        expectedProfileNames.add("ActiveEnerBalanceAC");
        expectedProfileNames.add("ReactiveEnerBalanceAC");
        expectedProfileNames.add("PowerQuadrant");
        expectedProfileNames.add("CurrentDirection");
        functionalProfiles.forEach(functionalProfile -> {
            if (functionalProfile.getName().equals("VoltageAC")) {
                checkVoltageACProfile(functionalProfile);
            }
            expectedProfileNames.remove(functionalProfile.getName());
        });
        // all profiles are reported
        assertTrue(expectedProfileNames.isEmpty());
    }

    private void checkFunctionalProfileDetailsVoltageAC(FunctionalProfile functionalProfile) {
        assertEquals("VoltageAC", functionalProfile.getProfileType());
        assertEquals("VoltageAC", functionalProfile.getName());
        assertEquals(FunctionalProfileCategory.SUB_METER_ELECTRICITY, functionalProfile.getCategory());
    }

    private void checkVoltageL1DataPoint(DataPoint dataPoint) {

        var expectedRange = new ArrayList<Value>();
        expectedRange.add(Float64Value.of(-Double.MAX_VALUE));
        expectedRange.add(Float64Value.of(Double.MAX_VALUE));

        assertEquals("VoltageL1", dataPoint.getName());
        assertEquals(DataType.FLOAT64, dataPoint.getDataType().getType());
        assertIterableEquals(expectedRange, dataPoint.getDataType().getRange());

        assertEquals(0.005, dataPoint.getMinimumValue());
        assertEquals(380.0, dataPoint.getMaximumValue());
        assertEquals(DataDirectionProduct.RW, dataPoint.getPermissions());
        assertEquals(1, dataPoint.getArrayLen());

        var expectedGenAttr = createGenericAttributeProduct("PrecisionPercent", "2.2", DataType.FLOAT32, Units.PERCENT, new ArrayList<>());
        var resultGenAttr = dataPoint.getGenericAttributes();
        assertTrue(resultGenAttr.stream().findFirst().isPresent());
        assertEquals(expectedGenAttr, resultGenAttr.stream().findFirst().get());
    }

    private void checkBaseUriConfigList(List<ConfigurationValue> configurationValues) {

        var configRecord = configurationValues.stream().findFirst();
        assertTrue(configRecord.isPresent());
        assertEquals("baseUri", configRecord.get().getName());
        assertEquals(DataType.STRING, configRecord.get().getDataType().getType());

        assertEquals("The base URI to connect to the service", configRecord.get().getDescriptions().get(Language.EN));
        assertEquals("Die Basis-URI des service", configRecord.get().getDescriptions().get(Language.DE));
    }

    @SuppressWarnings("SameParameterValue")
    private SGrModbusDevice createSGrModbusDevice(String deviceDescriptionXml) throws Exception {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL devDescUrl = classloader.getResource(deviceDescriptionXml);

        DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
        DeviceFrame devDesc = loader.load("", Optional.ofNullable(devDescUrl).map(URL::getPath).orElse(""));

        return new SGrModbusDevice(devDesc, modbusClientFactory);
    }

    @SuppressWarnings("SameParameterValue")
    private SGrRestApiDevice createSGrRestApiDevice(String deviceDescriptionXml) throws Exception {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL devDescUrl = classloader.getResource(deviceDescriptionXml);

        DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
        DeviceFrame devDesc = loader.load("", Optional.ofNullable(devDescUrl).map(URL::getPath).orElse(""));

        return new SGrRestApiDevice(devDesc, httpClientFactory);
    }

    private GenericAttribute createGenericAttributeProduct(
            String name, String value, DataType dataType, Units unit, List<GenericAttribute> children) {

        var dataTypeInfo = DataType.getDataTypeInfo(dataType).orElse(null);
        var dataTypeProduct = new DataTypeProduct();
        if (dataTypeInfo != null && UNKNOWN != dataTypeInfo.getType()) {
            dataTypeInfo.getSetGenValMethod().accept(dataTypeProduct, new EmptyType());
        }

        Value v = (value != null) ? Value.fromString(dataTypeProduct, value) : null;

        return new GenericAttribute(
                name,
                v,
                dataTypeInfo,
                unit,
                children);
    }

}
