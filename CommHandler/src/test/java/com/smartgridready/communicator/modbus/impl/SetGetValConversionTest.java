package com.smartgridready.communicator.modbus.impl;

import com.smartgridready.communicator.common.api.values.ArrayValue;
import com.smartgridready.communicator.common.api.values.DataType;
import com.smartgridready.ns.v0.BitOrder;
import com.smartgridready.ns.v0.BitmapEntryProduct;
import com.smartgridready.ns.v0.BitmapProduct;
import com.smartgridready.ns.v0.DataDirectionProduct;
import com.smartgridready.ns.v0.DataPointDescription;
import com.smartgridready.ns.v0.DataTypeProduct;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.EmptyType;
import com.smartgridready.ns.v0.EnumEntryProductRecord;
import com.smartgridready.ns.v0.EnumMapProduct;
import com.smartgridready.ns.v0.FunctionalProfileDescription;
import com.smartgridready.ns.v0.InterfaceList;
import com.smartgridready.ns.v0.ModbusBoolean;
import com.smartgridready.ns.v0.ModbusDataPoint;
import com.smartgridready.ns.v0.ModbusDataPointConfiguration;
import com.smartgridready.ns.v0.ModbusDataPointList;
import com.smartgridready.ns.v0.ModbusDataType;
import com.smartgridready.ns.v0.ModbusFunctionalProfile;
import com.smartgridready.ns.v0.ModbusFunctionalProfileList;
import com.smartgridready.ns.v0.ModbusInterface;
import com.smartgridready.ns.v0.ModbusInterfaceDescription;
import com.smartgridready.ns.v0.ModbusInterfaceSelection;
import com.smartgridready.ns.v0.ModbusRtu;
import com.smartgridready.ns.v0.RegisterType;
import com.smartgridready.ns.v0.TimeSyncBlockNotification;
import com.smartgridready.communicator.common.api.values.BitmapValue;
import com.smartgridready.communicator.common.api.values.BooleanValue;
import com.smartgridready.communicator.common.api.values.EnumValue;
import com.smartgridready.communicator.common.api.values.Float32Value;
import com.smartgridready.communicator.common.api.values.Float64Value;
import com.smartgridready.communicator.common.api.values.Int16UValue;
import com.smartgridready.communicator.common.api.values.Int16Value;
import com.smartgridready.communicator.common.api.values.Int32UValue;
import com.smartgridready.communicator.common.api.values.Int32Value;
import com.smartgridready.communicator.common.api.values.Int64UValue;
import com.smartgridready.communicator.common.api.values.Int64Value;
import com.smartgridready.communicator.common.api.values.Int8Value;
import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverModbusException;
import com.smartgridready.driver.api.modbus.GenDriverSocketException;
import com.smartgridready.communicator.modbus.helper.CacheRecord;
import com.smartgridready.communicator.modbus.helper.ModbusReaderResponse;
import io.vavr.Tuple2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test the data type conversions by writing a value to the modbus mock, read
 * the written value back and verify the read value.
 * <br>
 * <ul>
 *     <li>When converting from int type to int type, a double value is used as intermediate type.
 *     This leads to floating point precision deviations between written and read values.</li>
 *     <li>Several U8 type conversions are missing in setVal() and probably in getVal() too</li>
 * </ul>
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class SetGetValConversionTest {

    private static final Logger LOG = LoggerFactory.getLogger(SetGetValConversionTest.class);

    public static final int TIME_SYNC_BLOCK_SIZE = 6;
    public static final int TIME_SYNC_BLOCK_ADDRESS = 2000;

    @Mock
    GenDriverAPI4Modbus genDriverAPI4Modbus;

    @Captor
    ArgumentCaptor<int[]> intArrayCaptor = ArgumentCaptor.forClass(int[].class);

    @Captor
    ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(int.class);

    @Captor
    ArgumentCaptor<Boolean> booleanCaptor = ArgumentCaptor.forClass(Boolean.class);

    private static class Fixture<T> {

        Value writeValue;
        Value readValue;
        T expectedModbusValue;

        DeviceFrame deviceFrame;

        public Fixture(Value writeValue,
                       Value readValue,
                       T expectedModbusValue,
                       DeviceFrame deviceFrame) {
            this.writeValue = writeValue;
            this.expectedModbusValue = expectedModbusValue;
            this.deviceFrame = deviceFrame;
            this.readValue = readValue;
        }

        public Value getWriteValue() {
            return writeValue;
        }

        public T getExpectedModbusValue() {
            return expectedModbusValue;
        }

        public DeviceFrame getDeviceFrame() {
            return deviceFrame;
        }

        @Override
        public String toString() {
            return deviceFrame.getDeviceName();
        }
    }

    private static final Map<DataType, Map<DataType, Map<BitOrder, Fixture<int[]>>>>
            FIXT_TREE= new HashMap<>();

    static final class ValueProvider {

        Supplier<Value> randomGen;
        static ValueProvider of( Supplier<Value> randomGen) {
            ValueProvider vp = new ValueProvider();
            vp.randomGen = randomGen;
            return vp;
        }
    }

    static final Random RANDOM = new Random();

    static final EnumSet<BitOrder> ENUM_CONVERSION_FCTS;

    static final Map<DataType, ValueProvider> MODBUS_NUMBER_FORMATS = new HashMap<>();

    static final Map<DataType, ValueProvider> GEN_NUMBER_FORMATS = new HashMap<>();


    static {
        GEN_NUMBER_FORMATS.put(
                DataType.INT8,
                ValueProvider.of(() -> Int8Value.of((byte)(RANDOM.nextInt(2)==1 ? RANDOM.nextInt(127):-RANDOM.nextInt(127)))));

        GEN_NUMBER_FORMATS.put(
                DataType.INT16,
                ValueProvider.of(() -> Int16Value.of((short) RANDOM.nextInt(Short.MAX_VALUE))));

        GEN_NUMBER_FORMATS.put(
                DataType.INT32,
                ValueProvider.of(() -> Int32Value.of(RANDOM.nextInt(2)==1 ? RANDOM.nextInt(Integer.MAX_VALUE):-RANDOM.nextInt(Integer.MAX_VALUE))));

        GEN_NUMBER_FORMATS.put(
                DataType.INT64,
                ValueProvider.of(() -> Int64Value.of(RANDOM.nextInt(2)==1 ? RANDOM.nextLong():-RANDOM.nextLong())));

        GEN_NUMBER_FORMATS.put(
                DataType.INT8U,
                ValueProvider.of(() -> Int8Value.of((byte)RANDOM.nextInt(127))));

        GEN_NUMBER_FORMATS.put(
                DataType.INT16U,
                ValueProvider.of(() -> Int16UValue.of(RANDOM.nextInt(2*Short.MAX_VALUE))));

        GEN_NUMBER_FORMATS.put(
                DataType.INT32U,
                ValueProvider.of(() -> Int32UValue.of(RANDOM.nextInt())));

        GEN_NUMBER_FORMATS.put(
                DataType.INT64U,
                ValueProvider.of(() -> Int64Value.of(RANDOM.nextLong())));

        GEN_NUMBER_FORMATS.put(
                DataType.FLOAT32,
                ValueProvider.of(() -> Float32Value.of(100*RANDOM.nextFloat())));

        GEN_NUMBER_FORMATS.put(
                DataType.FLOAT64,
                ValueProvider.of(() -> Float64Value.of(100*RANDOM.nextDouble())));

        //=========================================================

        MODBUS_NUMBER_FORMATS.put(
                DataType.INT8,
                ValueProvider.of(() -> Int8Value.of((byte)(RANDOM.nextInt(2)==1 ? RANDOM.nextInt(127):-RANDOM.nextInt(127)))));

        MODBUS_NUMBER_FORMATS.put(
                DataType.INT16,
                ValueProvider.of(() -> Int16Value.of((short)RANDOM.nextInt(Short.MAX_VALUE))));

        MODBUS_NUMBER_FORMATS.put(
                DataType.INT32,
                ValueProvider.of(() -> Int32Value.of(RANDOM.nextInt(2)==1 ? RANDOM.nextInt(Integer.MAX_VALUE):-RANDOM.nextInt(Integer.MAX_VALUE))));

        MODBUS_NUMBER_FORMATS.put(
                DataType.INT64,
                ValueProvider.of(
                        () -> Int64Value.of(RANDOM.nextInt(2)==1 ? RANDOM.nextLong():-RANDOM.nextLong())));

        MODBUS_NUMBER_FORMATS.put(
                DataType.INT16U,
                ValueProvider.of(() -> Int16UValue.of(RANDOM.nextInt(2*Short.MAX_VALUE))));

        MODBUS_NUMBER_FORMATS.put(
                DataType.INT32U,
                ValueProvider.of(() -> Int32UValue.of(RANDOM.nextInt())));

        MODBUS_NUMBER_FORMATS.put(
                DataType.INT64U,
                ValueProvider.of(() -> Int64UValue.of(BigInteger.valueOf(RANDOM.nextLong()))));

        MODBUS_NUMBER_FORMATS.put(
                DataType.FLOAT32,
                ValueProvider.of(() -> Float32Value.of(100*RANDOM.nextFloat())));

        MODBUS_NUMBER_FORMATS.put(
                DataType.FLOAT64,
                ValueProvider.of(() -> Float64Value.of(100*RANDOM.nextDouble())));

        ENUM_CONVERSION_FCTS = EnumSet.allOf(BitOrder.class);
        ENUM_CONVERSION_FCTS.remove(BitOrder.CHANGE_BIT_ORDER); // not implemented yet

        createFixtureTree();
    }


    static void createFixtureTree() {
        // Start with all genTypes
        GEN_NUMBER_FORMATS.forEach((key, genValueProvider) -> FIXT_TREE.put(key, createModbus(key)));
    }

    static Map<DataType, Map<BitOrder, Fixture<int[]>>> createModbus(DataType genType) {
        Map<DataType, Map<BitOrder, Fixture<int[]>>> modbusTypesMap =
            new HashMap<>();
        MODBUS_NUMBER_FORMATS.forEach((key, value) -> modbusTypesMap.put(key, createFixtures(genType, key)));
        return modbusTypesMap;
    }

    private static Map<BitOrder, Fixture<int[]>> createFixtures(DataType genType, DataType modbusType) {

        Map<BitOrder, Fixture<int[]>> fixtureMap = new HashMap<>();

        ENUM_CONVERSION_FCTS.forEach( convType ->
                fixtureMap.put(convType, createFixture(genType, modbusType, convType)));

        return fixtureMap;
    }

    private static Fixture<int[]> createFixture(DataType genType, DataType modbusType, BitOrder convType) {

        Value usedVal = createTestNumber(genType, modbusType);

        return new Fixture<>(usedVal, usedVal, new int[getModbusBufferSize(modbusType)],
                    deviceFrame(true, convType, genType, modbusType));
    }

    private static Value createTestNumber(DataType genType, DataType modbusType) {

        final Set<DataType> genFloatTypes = Sets.newSet(
                DataType.FLOAT32,
                DataType.FLOAT64
        );

        final Set<DataType> genUnsignedTypes = Sets.newSet(
                DataType.INT8U,
                DataType.INT16U,
                DataType.INT32U,
                DataType.INT64U
        );

        final Set<DataType> modbusFloatTypes = Sets.newSet(
                DataType.FLOAT32,
                DataType.FLOAT64);

        final Set<DataType> modbusUnsignedTypes = Sets.newSet(
                DataType.INT8U,
                DataType.INT16U,
                DataType.INT32U,
                DataType.INT64U
        );


        Value genVal = GEN_NUMBER_FORMATS.get(genType).randomGen.get();
        Value modbVal = MODBUS_NUMBER_FORMATS.get(modbusType).randomGen.get();

        Value usedVal = genVal;
        if (Math.abs(genVal.getFloat64()) > Math.abs(modbVal.getFloat64())) {
            usedVal = modbVal;
        }

        if (genUnsignedTypes.contains(genType) || modbusUnsignedTypes.contains(modbusType)) {
            // one of the types is unsigned, modify the selected value to be positive
            usedVal.absValue();
        }

        if ( !genFloatTypes.contains(genType) || !modbusFloatTypes.contains(modbusType)) {
            // one of them is an integer.
            usedVal.roundToInt();
        }
        return usedVal;
    }


    static  Stream<Fixture<int[]>> streamFixtureTree() {

        createFixtureTree();

        final List<Fixture<int[]>> fixtures = new ArrayList<>();

        FIXT_TREE.values().forEach(
             modbusType -> modbusType.values().forEach(convType -> fixtures.addAll(convType.values()))
        );
        return fixtures.stream();
    }

    @ParameterizedTest
    @MethodSource("streamFixtureTree")
    void testCompleteConversion(Fixture<int[]> fixture) throws Exception {

        doTestWriteAndReadBack(fixture);
    }

    @Test
    void testSingleFixture() throws Exception {

        Fixture<int[]> fixture =
                new Fixture<>(Int8Value.of((byte)18), Int8Value.of((byte)18), new int[]{ 0, 18},
                        deviceFrame(true, BitOrder.CHANGE_BYTE_ORDER, DataType.INT8, DataType.INT8));

        doTestWriteAndReadBack(fixture);
    }

    @Test
    void testConvertString() throws Exception{

        Fixture<int[]> fixture =
                new Fixture<>(StringValue.of("Hello@SGr"), StringValue.of("Hello@SGr"), new int[]{0x4865, 0x6C6C, 0x6F40,0x5347, 0x7200},
                        deviceFrame(true, BitOrder.BIG_ENDIAN, DataType.STRING, DataType.STRING));

        doTestWriteAndReadBack(fixture);
    }


    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testBooleanRegisterConversion(boolean bVal)  throws Exception {
        Fixture<int[]> fixture =
                new Fixture<>(BooleanValue.of(bVal), BooleanValue.of(bVal), bVal ? new int[]{0x1} : new int[]{0x0},
                        deviceFrame(true, BitOrder.BIG_ENDIAN, DataType.BOOLEAN, DataType.BOOLEAN));

        doTestWriteAndReadBack(fixture);
    }


    private static class BooleanMapping {
        boolean writeVal;
        boolean mappingDefinedForFalse;
        int mappingVal;
        int[] expectedModbusVal;
        boolean expectedReadVal;

        public BooleanMapping(
                boolean writeVal, boolean mappingDefinedForFalse, int mappingVal,
                int[] expectedModbusVal, boolean expectedReadVal) {
            this.writeVal = writeVal;
            this.mappingDefinedForFalse = mappingDefinedForFalse;
            this.mappingVal = mappingVal;
            this.expectedModbusVal = expectedModbusVal;
            this.expectedReadVal = expectedReadVal;
        }

        @Override
        public String toString() {
            return String.format("writeVal=%b, mappingForFalse=%b, mappingVal=%d", writeVal, mappingDefinedForFalse, mappingVal);
        }
    }

    private Stream<BooleanMapping> booleanConversionsWithMappings() {

        List<BooleanMapping> testSet = new LinkedList<>();
        testSet.add(new BooleanMapping(false, true,  255, new int[]{255}, false));
        testSet.add(new BooleanMapping(false, false, 255, new int[]{0},   false));
        testSet.add(new BooleanMapping(true, true,    15, new int[]{0}, true));
        testSet.add(new BooleanMapping(true, false,   15, new int[]{15}, true));
        return testSet.stream();
    }


    @ParameterizedTest
    @MethodSource("booleanConversionsWithMappings")
    void testBooleanRegisterConversionWithMapping(BooleanMapping mapping) throws Exception {

        Fixture<int[]> fixture =
                new Fixture<>(
                        BooleanValue.of(mapping.writeVal),
                        BooleanValue.of(mapping.expectedReadVal),
                        mapping.expectedModbusVal,
                        deviceFrame(true, BitOrder.BIG_ENDIAN, DataType.BOOLEAN, DataType.BOOLEAN));

        // Add a boolean mapping to the modbus datapoint.
        ModbusDataPoint modbusDataPoint = fixture.getDeviceFrame()
                .getInterfaceList()
                .getModbusInterface()
                .getFunctionalProfileList()
                .getFunctionalProfileListElement().get(0)
                .getDataPointList().getDataPointListElement().get(0);


        ModbusBoolean modbusBoolean = new ModbusBoolean();
        if (mapping.mappingDefinedForFalse) {
            modbusBoolean.setFalseValue(mapping.mappingVal);
        } else {
            modbusBoolean.setTrueValue(mapping.mappingVal);
        }

        ModbusDataType modbusDataTypeBoolean = new ModbusDataType();
        modbusDataTypeBoolean.setBoolean(modbusBoolean);
        modbusDataPoint.getModbusDataPointConfiguration().setModbusDataType(modbusDataTypeBoolean);

        // Test
        doTestWriteAndReadBack(fixture);
    }

    static Stream<Tuple2<BitOrder, Boolean>> booleanConversions() {

        List<Tuple2<BitOrder, Boolean>> testParams = new ArrayList<>();

        testParams.add(new Tuple2<>(BitOrder.BIG_ENDIAN, true));
        testParams.add(new Tuple2<>(BitOrder.BIG_ENDIAN, false));
        testParams.add(new Tuple2<>(BitOrder.CHANGE_BIT_ORDER, true));
        testParams.add(new Tuple2<>(BitOrder.CHANGE_BIT_ORDER, false));
        testParams.add(new Tuple2<>(BitOrder.CHANGE_WORD_ORDER, true));
        testParams.add(new Tuple2<>(BitOrder.CHANGE_WORD_ORDER, false));
        testParams.add(new Tuple2<>(BitOrder.CHANGE_D_WORD_ORDER, true));
        testParams.add(new Tuple2<>(BitOrder.CHANGE_D_WORD_ORDER, false));
        return  testParams.stream();
    }


    @ParameterizedTest
    @MethodSource("booleanConversions")
    void readWriteCoilsTest(Tuple2<BitOrder, Boolean> testParam) throws Exception {

        DeviceFrame deviceFrame = deviceFrame(
                false,
                testParam._1(),
                DataType.BOOLEAN,
                DataType.BOOLEAN);

        deviceFrame.getInterfaceList().getModbusInterface().getFunctionalProfileList().getFunctionalProfileListElement().get(0)
                .getDataPointList().getDataPointListElement().get(0)
                .getModbusDataPointConfiguration().setRegisterType(RegisterType.COIL);

        Fixture<boolean[]> fixture = new Fixture<>(BooleanValue.of(testParam._2), BooleanValue.of(testParam._2),
                new boolean[]{testParam._2()}, deviceFrame);

        when(genDriverAPI4Modbus.readCoils(anyShort(), anyInt(), anyInt())).thenReturn(new boolean[]{testParam._2} );

        SGrModbusDevice modbusDevice = new SGrModbusDevice(
                fixture.getDeviceFrame(),           // model
                genDriverAPI4Modbus);                     // mock

        modbusDevice.setVal("ActivePowerAC", "ActivePowerACL1", fixture.getWriteValue());
        verify(genDriverAPI4Modbus).writeSingleCoil(anyShort(), anyInt(), booleanCaptor.capture());

        LOG.info("Modbus write coils: {}", booleanCaptor.getAllValues().isEmpty() ? "-" : booleanCaptor.getValue());
        assertEquals(fixture.getExpectedModbusValue()[0], booleanCaptor.getValue());
        when(genDriverAPI4Modbus.readCoils(anyShort(), anyInt(), anyInt())).thenReturn(new boolean[]{booleanCaptor.getValue()});

        Value res = modbusDevice.getVal("ActivePowerAC", "ActivePowerACL1");

        verify(genDriverAPI4Modbus).readCoils(anyShort(), anyInt(), anyInt());
        LOG.info("Modbus read value: {}", res.getBoolean());
        assertEquals(fixture.readValue.getBoolean(), res.getBoolean());
    }

    @Test
    void testEnumConversion() throws Exception {

        DataTypeProduct dataTypeProduct = new DataTypeProduct();
        dataTypeProduct.setEnum(createEnumMap(new byte[]{(byte)0xFF}));

        DeviceFrame deviceFrame = deviceFrame(false, BitOrder.BIG_ENDIAN, DataType.ENUM, DataType.INT32);
        deviceFrame.getInterfaceList().getModbusInterface().getFunctionalProfileList()
                .getFunctionalProfileListElement().get(0)
                .getDataPointList().getDataPointListElement().get(0)
                .getDataPoint().setDataType(dataTypeProduct);

        SGrModbusDevice modbusDevice = new SGrModbusDevice(deviceFrame, genDriverAPI4Modbus);

        modbusDevice.setVal("ActivePowerAC", "ActivePowerACL1", EnumValue.of("DHW_PUSH"));
        verify(genDriverAPI4Modbus).writeMultipleRegisters(anyShort(), anyInt(), intArrayCaptor.capture());

        if (!intArrayCaptor.getAllValues().isEmpty()) {
            LOG.info("Modbus write enum: {}", intArrayCaptor.getValue());
            assertArrayEquals(new int[]{0, 255}, intArrayCaptor.getValue());
            when(genDriverAPI4Modbus.readHoldingRegisters(anyShort(), anyInt(), anyInt())).thenReturn(intArrayCaptor.getValue());
        }

        Value res = modbusDevice.getVal("ActivePowerAC", "ActivePowerACL1");
        LOG.info("Modbus read value: {}", res);
        assertEquals("DHW_PUSH", res.getEnum().getLiteral());
        assertEquals("DHW_PUSH", res.getString());
        assertEquals(255, res.getInt32());
        assertEquals("Description of DHW_PUSH", res.getEnum().getDescription());
        assertEquals("DHW_PUSH:255 | Description of DHW_PUSH", res.toString());
    }

    @Test
    void testBitmapConversion() throws Exception {

        int[] expectedModbusValue = new int[]{0x00000004, 0x0000010B};

        DataTypeProduct dataTypeProduct = new DataTypeProduct();
        dataTypeProduct.setBitmap(createBitmap());

        DeviceFrame deviceFrame = deviceFrame(false, BitOrder.BIG_ENDIAN, DataType.BITMAP, DataType.INT32U);
        deviceFrame.getInterfaceList().getModbusInterface().getFunctionalProfileList()
                .getFunctionalProfileListElement().get(0)
                .getDataPointList().getDataPointListElement().get(0)
                .getDataPoint().setDataType(dataTypeProduct);

        SGrModbusDevice modbusDevice = new SGrModbusDevice(deviceFrame, genDriverAPI4Modbus);

        Map<String, Boolean> bitsToSet = new HashMap<>();
        bitsToSet.put("BIT_0", true);
        bitsToSet.put("BIT_1", true);
        bitsToSet.put("BIT_3", true);
        bitsToSet.put("BIT_8", true);
        bitsToSet.put("BIT_18", true);

        modbusDevice.setVal("ActivePowerAC", "ActivePowerACL1", BitmapValue.of(bitsToSet));

        verify(genDriverAPI4Modbus).writeMultipleRegisters(anyShort(), anyInt(), intArrayCaptor.capture());
        LOG.info("Modbus write bitmap: {}", intArrayCaptor.getValue());
        assertArrayEquals(expectedModbusValue, intArrayCaptor.getValue());

        when(genDriverAPI4Modbus.readHoldingRegisters(anyShort(), anyInt(), anyInt())).thenReturn(intArrayCaptor.getValue());

        Value res = modbusDevice.getVal("ActivePowerAC", "ActivePowerACL1");
        LOG.info("Modbus read value: {}", res);

        Map<String, Boolean> bitmapRecords = res.getBitmap();
        assertEquals(true, bitmapRecords.get("BIT_1"));
        assertEquals(false, bitmapRecords.get("BIT_2"));
        assertEquals(true, bitmapRecords.get("BIT_3"));
        assertEquals(false, bitmapRecords.get("BIT_4"));
        assertEquals(false, bitmapRecords.get("BIT_5"));
        assertEquals(false, bitmapRecords.get("BIT_6"));
        assertEquals(false, bitmapRecords.get("BIT_7"));

        assertEquals(true, bitmapRecords.get("BIT_8"));
        assertEquals(false, bitmapRecords.get("BIT_16"));
        assertEquals(false, bitmapRecords.get("BIT_17"));
        assertEquals(true, bitmapRecords.get("BIT_18"));
    }

    @Test
    void testBitmapModification() throws Exception {

        DataTypeProduct dataTypeProduct = new DataTypeProduct();
        dataTypeProduct.setBitmap(createBitmap());

        DeviceFrame deviceFrame = deviceFrame(false, BitOrder.BIG_ENDIAN, DataType.BITMAP, DataType.INT32U);
        deviceFrame.getInterfaceList().getModbusInterface().getFunctionalProfileList()
                .getFunctionalProfileListElement().get(0)
                .getDataPointList().getDataPointListElement().get(0)
                .getDataPoint().setDataType(dataTypeProduct);

        SGrModbusDevice modbusDevice = new SGrModbusDevice(deviceFrame, genDriverAPI4Modbus);

        int[] expectedModbusValue = new int[]{0x00000004, 0x0000010B};
        when(genDriverAPI4Modbus.readHoldingRegisters(anyShort(), anyInt(), anyInt())).thenReturn(expectedModbusValue);


        Value res = modbusDevice.getVal("ActivePowerAC", "ActivePowerACL1");
        LOG.info("Modbus read value: {}", res);

        Map<String, Boolean> bitmapRecords = res.getBitmap();
        assertEquals(true, bitmapRecords.get("BIT_1"));
        assertEquals(false, bitmapRecords.get("BIT_2"));
        assertEquals(true, bitmapRecords.get("BIT_3"));
        assertEquals(false, bitmapRecords.get("BIT_4"));
        assertEquals(false, bitmapRecords.get("BIT_5"));
        assertEquals(false, bitmapRecords.get("BIT_6"));
        assertEquals(false, bitmapRecords.get("BIT_7"));

        assertEquals(true, bitmapRecords.get("BIT_8"));
        assertEquals(false, bitmapRecords.get("BIT_16"));
        assertEquals(false, bitmapRecords.get("BIT_17"));
        assertEquals(true, bitmapRecords.get("BIT_18"));


        // Set bits depending on returned value:
        Mockito.reset(genDriverAPI4Modbus);
        expectedModbusValue = new int[]{0x00000007, 0x00000100};

        Map<String, Boolean>  modifiedBitmap = res.getBitmap();
        modifiedBitmap.put("BIT_0", false);
        modifiedBitmap.put("BIT_1", false);
        modifiedBitmap.put("BIT_3", false);
        modifiedBitmap.put("BIT_16", true);
        modifiedBitmap.put("BIT_17", true);

        modbusDevice.setVal("ActivePowerAC", "ActivePowerACL1", BitmapValue.of(modifiedBitmap));


        verify(genDriverAPI4Modbus).writeMultipleRegisters(anyShort(), anyInt(), intArrayCaptor.capture());
        LOG.info("Modbus write bitmap: {}", intArrayCaptor.getValue());
        assertArrayEquals(expectedModbusValue, intArrayCaptor.getValue());

        when(genDriverAPI4Modbus.readHoldingRegisters(anyShort(), anyInt(), anyInt())).thenReturn(intArrayCaptor.getValue());

        res = modbusDevice.getVal("ActivePowerAC", "ActivePowerACL1");
        LOG.info("Modbus read value: {}", res);

        Map<String, Boolean> modifiedBits = res.getBitmap();

        assertEquals(false, modifiedBits.get("BIT_0"));
        assertEquals(false, modifiedBits.get("BIT_1"));
        assertEquals(false, modifiedBits.get("BIT_2"));
        assertEquals(false, modifiedBits.get("BIT_3"));
        assertEquals(false, modifiedBits.get("BIT_4"));
        assertEquals(false, modifiedBits.get("BIT_5"));
        assertEquals(false, modifiedBits.get("BIT_6"));
        assertEquals(false, modifiedBits.get("BIT_7"));

        assertEquals(true, modifiedBits.get("BIT_8"));
        assertEquals(true, modifiedBits.get("BIT_16"));
        assertEquals(true, modifiedBits.get("BIT_17"));
        assertEquals(true, modifiedBits.get("BIT_18"));
    }

    @Test
    void testSetValGetValArray() throws Exception {

        DeviceFrame deviceFrame =
                        deviceFrame(false, BitOrder.CHANGE_BYTE_ORDER, DataType.FLOAT32, DataType.FLOAT32);

        Value[] expectedValues = new Value[]{Float32Value.of(219.923f),Float32Value.of(220.000f), Float32Value.of(220.128f)};
        int[] expectedModbusValues = new int[]{23363, 19180, 23619, 0, 23619, -15072};


        when(genDriverAPI4Modbus.readHoldingRegisters(anyShort(), anyInt(), anyInt())).thenReturn(expectedModbusValues);

        SGrModbusDevice modbusDevice = new SGrModbusDevice(
                deviceFrame,                              // model
                genDriverAPI4Modbus);                     // mock


        var values = ArrayValue.of(expectedValues);

        modbusDevice.setVal("ActivePowerAC", "ActivePowerAC-ARRAY", values);
        verify(genDriverAPI4Modbus, atLeast(1)).writeMultipleRegisters(anyShort(), anyInt(), intArrayCaptor.capture());

        if (!intArrayCaptor.getAllValues().isEmpty()) {
            int[] modbusreg = intArrayCaptor.getValue();
            assertEquals(getModbusBufferSize(
                    DataType.FLOAT32) * expectedValues.length, modbusreg.length,
                    "Invalid length of values written to the modbus registers.");

            modbusreg = adjustSign(modbusreg);
            LOG.info("Modbus write-read-back registers: {}", intArrayToShortHex(modbusreg));
            assertArrayEquals(expectedModbusValues, modbusreg);
            when(genDriverAPI4Modbus.readHoldingRegisters(anyShort(), anyInt(), anyInt())).thenReturn(modbusreg);
        }

        Value[] res = modbusDevice.getVal("ActivePowerAC", "ActivePowerAC-ARRAY").asArray();
        LOG.info("Modbus read values: {}", Arrays.toString(res));
        assertArrayEquals(
                Arrays.stream(expectedValues).map(Value::toString).toArray(),
                Arrays.stream(res).map(Value::toString).toArray());
    }

    @Test
    void testSetValGetValBlocktransfer() throws Exception {

        DeviceFrame deviceFrame =
                deviceFrame(false, BitOrder.CHANGE_BYTE_ORDER, DataType.FLOAT32, DataType.FLOAT32);

        Value expectedValue = Float32Value.of(220.220f);
        int[] expectedModbusValues = new int[]{23619, 21048, 23619, 21048, 23619, 21048};


        SGrModbusDevice modbusDevice = new SGrModbusDevice(
                deviceFrame,                              // model
                genDriverAPI4Modbus);                     // mock


        modbusDevice.setVal("ActivePowerAC", "ActivePowerAC-BLOCK", expectedValue);
        verify(genDriverAPI4Modbus).writeMultipleRegisters(anyShort(), anyInt(), intArrayCaptor.capture());
        LOG.info("Modbus write multiple registers (single value): {}", intArrayCaptor.getAllValues().isEmpty() ? "-" : intArrayToShortHex(intArrayCaptor.getValue()));

        if (!intArrayCaptor.getAllValues().isEmpty()) {
            int[] modbusreg = intArrayCaptor.getValue();
            assertEquals(getModbusBufferSize(DataType.FLOAT32), modbusreg.length,
                    "Invalid length of values written to the modbus registers.");

            IntBuffer intBuf = IntBuffer.allocate(TIME_SYNC_BLOCK_SIZE);

            for (int i=0; i<TIME_SYNC_BLOCK_SIZE/getModbusBufferSize(DataType.FLOAT32); i++) {
                intBuf.put(modbusreg);
            }

            int[] modbusregRead = intBuf.array();
            assertArrayEquals(expectedModbusValues, modbusregRead);
            modbusregRead = adjustSign(modbusregRead);
            LOG.info("Modbus read multiple registers: {}", intArrayToShortHex(modbusregRead));
            when(genDriverAPI4Modbus.readHoldingRegisters(anyShort(), anyInt(), anyInt())).thenReturn(modbusregRead);
        }

        // Mock the read cache before doing the read test.
        Field timeSyncBlockCache = ReflectionUtils.findFields(SGrModbusDevice.class,
                f -> f.getName().equals("myTimeSyncBlockReadCache"),
                ReflectionUtils.HierarchyTraversalMode.TOP_DOWN).get(0);
        timeSyncBlockCache.setAccessible(true);

        Map<TimeSyncBlockNotification, CacheRecord<ModbusReaderResponse>> cacheRecords = new HashMap<>();
        timeSyncBlockCache.set(modbusDevice, cacheRecords);

        // Test the block read.
        Value res = modbusDevice.getVal("ActivePowerAC", "ActivePowerAC-BLOCK");
        verify(genDriverAPI4Modbus).readHoldingRegisters((short)1, TIME_SYNC_BLOCK_ADDRESS, TIME_SYNC_BLOCK_SIZE);

        assertEquals(1, cacheRecords.size(), "Cache has not been written during modbus read.");

        cacheRecords.values().forEach(value -> assertArrayEquals(expectedModbusValues, value.getValue().getMbregresp(),
                "Cache record does not contain the expected modbus response (3* the given float value)"));

        LOG.info("Read cache content: {}", cacheRecords);
        LOG.info("Modbus read value: {}", res);
        assertEquals(expectedValue.getString(), res.getString().trim());


        // Read the value again, will use the cache now...
        res = modbusDevice.getVal("ActivePowerAC", "ActivePowerAC-BLOCK");
        assertEquals(expectedValue.getString(), res.getString().trim());
    }

    private void doTestWriteAndReadBack(Fixture<int[]> fixture) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        // given
        SGrModbusDevice modbusDevice = new SGrModbusDevice(
                fixture.getDeviceFrame(),           // model
                genDriverAPI4Modbus);                     // mock

        // when
        LOG.info("Modbus write value: {}", fixture.getWriteValue());
        modbusDevice.setVal("ActivePowerAC", "ActivePowerACL1", fixture.getWriteValue());

        // then
        verify(genDriverAPI4Modbus, atLeast(0)).writeMultipleRegisters(anyShort(), anyInt(), intArrayCaptor.capture());
        verify(genDriverAPI4Modbus, atLeast(0)).writeSingleRegister(anyShort(), anyInt(), intCaptor.capture());

        if (!intArrayCaptor.getAllValues().isEmpty()) {
            int[] modbusRegReceived = intArrayCaptor.getValue();
            LOG.info("Modbus write-read-back multiple registers: {}", intArrayToShortHex(modbusRegReceived));

            assertEquals(fixture.getExpectedModbusValue().length, modbusRegReceived.length,
                    "Invalid length of values written to the modbus registers.");

            when(genDriverAPI4Modbus.readHoldingRegisters(anyShort(), anyInt(), anyInt())).thenReturn(modbusRegReceived);

        } else if (!intCaptor.getAllValues().isEmpty()){
            LOG.info("Modbus write-read-back single register: {}", Integer.toHexString(intCaptor.getValue()));
            when(genDriverAPI4Modbus.readHoldingRegisters(anyShort(), anyInt(), anyInt())).thenReturn(new int[]{intCaptor.getValue()});
        }

        Value resVal = modbusDevice.getVal("ActivePowerAC", "ActivePowerACL1");
        LOG.info("Modbus read value: {}", resVal);

        // check whether single value is available as array too:
        assertEquals(resVal, resVal.asArray()[0]);

        // We need to normalize the String values to compare, since the values can be mixed int/float typed.
        if (resVal instanceof StringValue || resVal instanceof BooleanValue) {
            assertEquals(fixture.getWriteValue().getString(), resVal.getString());
        } else {
            Double expected = Double.parseDouble(fixture.getWriteValue().getString());
            String expStr = String.format("%.4f", expected);
            Double result = Double.parseDouble(resVal.getString());
            String resStr = String.format("%.4f", result);
            assertEquals(expStr, resStr);
        }
    }


    private static DeviceFrame deviceFrame(boolean firstRegOne,
                                                    BitOrder conversionFct,
                                                    DataType genericType,
                                                    DataType modbusType) {

        DeviceFrame deviceFrame = new DeviceFrame();
        deviceFrame.setDeviceName(createDeviceName(conversionFct, genericType, modbusType));

        InterfaceList interfaceList = new InterfaceList();
        deviceFrame.setInterfaceList(interfaceList);

        ModbusInterface modbusInterface = new ModbusInterface();
        interfaceList.setModbusInterface(modbusInterface);
        deviceFrame.getInterfaceList().setModbusInterface(modbusInterface);

        modbusInterface.setModbusInterfaceDescription(modbDevDesc(firstRegOne, conversionFct));

        ModbusFunctionalProfile functionalProfile = new ModbusFunctionalProfile();

        ModbusDataPointList dataPointList = new ModbusDataPointList();
        functionalProfile.setDataPointList(dataPointList);

        FunctionalProfileDescription profileDescription = new FunctionalProfileDescription();
        profileDescription.setFunctionalProfileName("ActivePowerAC");
        functionalProfile.setFunctionalProfile(profileDescription);

        functionalProfile.getDataPointList().getDataPointListElement().add(modbDp(genericType, modbusType, "ActivePowerACL1", 1));

        if ((genericType != DataType.ENUM) && (genericType != DataType.BITMAP)) {
            // This does not work with enums. The genericValue and modbusValue of the previous datapoint will be set to <null>
            functionalProfile.getDataPointList().getDataPointListElement().add(modbDp(genericType, modbusType, "ActivePowerAC-ARRAY", 3));
            functionalProfile.getDataPointList().getDataPointListElement().add(blockDp(genericType, modbusType));
            deviceFrame.getInterfaceList().getModbusInterface().getTimeSyncBlockNotification().add(timeSyncBlock());
        }

        ModbusFunctionalProfileList profileList = new ModbusFunctionalProfileList();
        profileList.getFunctionalProfileListElement().add(functionalProfile);
        deviceFrame.getInterfaceList().getModbusInterface().setFunctionalProfileList(profileList);
        return deviceFrame;
    }


    private static ModbusInterfaceDescription modbDevDesc(boolean firstRegisterOne,
            BitOrder modbusConversionScheme) {

        ModbusInterfaceDescription mbDesc = new ModbusInterfaceDescription();

        ModbusRtu mbRtu = new ModbusRtu();
        mbRtu.setSlaveAddr(String.valueOf(1));
        mbRtu.setPortName("COM123");    // required or interface is not recognized as serial RTU

        mbDesc.setFirstRegisterAddressIsOne(firstRegisterOne);
        mbDesc.setBitOrder(modbusConversionScheme);

        mbDesc.setModbusInterfaceSelection(ModbusInterfaceSelection.RTU);
        mbDesc.setModbusRtu(mbRtu);

        return mbDesc;
    }

    private static ModbusDataPoint modbDp(DataType genericType, DataType modbusType, String dpName, int arrLen) {

        ModbusDataPoint modbDp = new ModbusDataPoint();
        modbDp.setDataPoint(genDpDesc(genericType, dpName, arrLen));
        modbDp.setModbusDataPointConfiguration(dpModbDesc(modbusType));
        return modbDp;
    }

    private static ModbusDataPoint blockDp(DataType genericType, DataType modbusType) {
        ModbusDataPoint blockDp = modbDp(genericType, modbusType, "ActivePowerAC-BLOCK", 1);
        blockDp.setBlockCacheIdentification("ActivePowerAC-BLOCK");
        return blockDp;
    }

    private static ModbusDataPointConfiguration dpModbDesc(DataType modbusType) {

        ModbusDataPointConfiguration modbDpDesc = new ModbusDataPointConfiguration();
        modbDpDesc.setAddress(BigInteger.valueOf(2000));
        modbDpDesc.setRegisterType(RegisterType.HOLD_REGISTER);
        modbDpDesc.setBitRank((short) 0);
        modbDpDesc.setModbusDataType(modbusDP(modbusType));
        modbDpDesc.setNumberOfRegisters(getModbusBufferSize(modbusType));

        return modbDpDesc;
    }

    private static DataPointDescription genDpDesc(DataType genType, String dpName, int arrLen) {

        DataPointDescription genDpDesc = new DataPointDescription();
        genDpDesc.setDataPointName(dpName);
        genDpDesc.setDataType(basicDP(genType));
        genDpDesc.setDataDirection(DataDirectionProduct.RW);
        if (arrLen > 1) {
            genDpDesc.setArrayLength(arrLen);
        }
        return genDpDesc;
    }

    private static DataTypeProduct basicDP(DataType v0PackageType) {

        DataTypeProduct dp = new DataTypeProduct();

        if(v0PackageType == DataType.ENUM) {
            dp.setEnum(new EnumMapProduct());
        } else if (v0PackageType == DataType.BITMAP) {
            dp.setBitmap(new BitmapProduct());
        } else if (DataType.getDataTypeInfo(v0PackageType).isPresent()) {
            DataType.getDataTypeInfo(v0PackageType).get().getSetGenValMethod().accept(dp, new EmptyType());
        }
        return dp;
    }

    private static ModbusDataType modbusDP(DataType v0PackageType) {

        ModbusDataType dp = new ModbusDataType();
        if (v0PackageType == DataType.BOOLEAN) {
            dp.setBoolean(new ModbusBoolean());
        } else if (DataType.getDataTypeInfo(v0PackageType).isPresent()){
            DataType.getDataTypeInfo(v0PackageType).get().getSetModbusValMethod().accept(dp, new EmptyType());
        }
        return dp;
    }


    private static TimeSyncBlockNotification timeSyncBlock() {

        TimeSyncBlockNotification tsBlock = new TimeSyncBlockNotification();
        tsBlock.setFirstAddress(BigInteger.valueOf(TIME_SYNC_BLOCK_ADDRESS));
        tsBlock.setBlockCacheIdentification("ActivePowerAC-BLOCK");
        tsBlock.setRegisterType(RegisterType.HOLD_REGISTER);
        tsBlock.setTimeToLiveMs(200);
        tsBlock.setSize(TIME_SYNC_BLOCK_SIZE);
        return tsBlock;
    }

    private static int getModbusBufferSize(DataType modbusType) {

        if (modbusType == DataType.FLOAT64
                || modbusType == DataType.INT64U
                || modbusType == DataType.INT64)
        {
            return 4;
        } else if (   modbusType == DataType.INT8
                   || modbusType == DataType.INT8U
                   || modbusType == DataType.INT16
                   || modbusType == DataType.INT16U
                   || modbusType == DataType.BOOLEAN
                   || modbusType == DataType.ENUM) {
            return 1;
        } else if ( modbusType == DataType.STRING) {
            return 5;
        }
        return 2;
    }

    private static String createDeviceName(BitOrder bitOrder, DataType genType, DataType modbusType) {
        return genType.name() + " - "
                + modbusType.name() + " - "
                + bitOrder.name();
    }

    private static int[] adjustSign(int[] registers) {
        IntBuffer buffer = IntBuffer.allocate(registers.length);
        for (int reg : registers) {
            buffer.put(adjustSign(reg));
        }
        return buffer.array();
    }

    private static int adjustSign(int register) {
        if ( (register & 0x8000) != 0) {
            return register | 0xFFFF0000;
        }
        return register;
    }

    @Test
    void testAdjustSign() {

        byte[] regPos = new byte[]{(byte)0x00, (byte)0x00, (byte)0x7F, (byte)0xAA};
        byte[] regNeg = new byte[]{(byte)0x00, (byte)0x00, (byte)0xAF, (byte)0xBB};
        byte[] regNegExpect = new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xAF, (byte)0xBB};

        assertEquals(ByteBuffer.wrap(regPos).getInt(),  adjustSign(ByteBuffer.wrap(regPos).getInt()));

        assertEquals(ByteBuffer.wrap(regNegExpect).getInt(),  adjustSign(ByteBuffer.wrap(regNeg).getInt()));
    }

    private static String intArrayToShortHex(int[] values) {
        StringBuilder sb = new StringBuilder("[");
        Arrays.stream(values).forEach(
                val -> sb.append(Integer.toHexString(val)).append(",")
        );
        sb.delete(sb.length() -1, sb.length()).append("]");
        return sb.toString();
    }

    private EnumMapProduct createEnumMap(byte[] hexMask) {
        EnumMapProduct retVal = new EnumMapProduct();
        List<EnumEntryProductRecord> enumEntries = retVal.getEnumEntry();
        enumEntries.add(createEnumRecord("DHW_ON", 1));
        enumEntries.add(createEnumRecord("DHW_OFF", 2));
        enumEntries.add(createEnumRecord("DHW_PUSH", 255));
        enumEntries.add(createEnumRecord("DHW_TMP_OFF", 256));
        retVal.setHexMask(hexMask);
        return retVal;
    }

    private EnumEntryProductRecord createEnumRecord(String literal, int ordinal) {
        EnumEntryProductRecord enumRecord = new EnumEntryProductRecord();
        enumRecord.setLiteral(literal);
        enumRecord.setOrdinal(ordinal);
        enumRecord.setDescription("Description of " + literal);
        return enumRecord;
    }

    private static BitmapProduct createBitmap() {

        BitmapProduct bitmap = new BitmapProduct();

        List<BitmapEntryProduct> bitmapEntries = bitmap.getBitmapEntry();
        bitmapEntries.add(createBitmapEntry("BIT_0", new byte[]{1}));
        bitmapEntries.add(createBitmapEntry("BIT_1", new byte[]{2}));
        bitmapEntries.add(createBitmapEntry("BIT_2", new byte[]{4}));
        bitmapEntries.add(createBitmapEntry("BIT_3", new byte[]{8}));
        bitmapEntries.add(createBitmapEntry("BIT_4", new byte[]{16}));
        bitmapEntries.add(createBitmapEntry("BIT_5", new byte[]{32}));
        bitmapEntries.add(createBitmapEntry("BIT_6", new byte[]{64}));
        bitmapEntries.add(createBitmapEntry("BIT_7", new byte[]{(byte)128}));

        bitmapEntries.add(createBitmapEntry("BIT_8",  new byte[]{0x01, 0x00}));
        bitmapEntries.add(createBitmapEntry("BIT_16", new byte[]{0x01, 0x00, 0x00}));
        bitmapEntries.add(createBitmapEntry("BIT_17", new byte[]{0x02, 0x00, 0x00}));
        bitmapEntries.add(createBitmapEntry("BIT_18", new byte[]{0x04, 0x00, 0x00}));

        return bitmap;
    }

    private static BitmapEntryProduct createBitmapEntry(String name, byte[] hexMask) {
        BitmapEntryProduct entry = new BitmapEntryProduct();
        entry.setLiteral(name);
        entry.setHexMask(hexMask);
        entry.setDescription("Description of " + name);
        return entry;
    }
}
