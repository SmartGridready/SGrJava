package com.smartgridready.communicator.common.api;

import com.smartgridready.ns.v0.EmptyType;
import com.smartgridready.ns.v0.ModbusDataType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS) // Needed to have a non static @MethodSource
class StringValueTest {

    private static final ModbusDataType modbusDataTypeString = new ModbusDataType();
    static { modbusDataTypeString.setString(new EmptyType());
    }

    final static class Fixture {
        String strValue;
        Number expectedValue;
        Supplier<Number> getFunction;

        public Fixture(String strValue, Number expectedValue, Supplier<Number> getFunction) {
            this.strValue = strValue;
            this.expectedValue = expectedValue;
            this.getFunction = getFunction;
        }

        @Override
        public String toString() {
            return  strValue;
        }
    }

    @Test
    void modbusReadWrite() {

        final int[] modbusVal = new int[]{0x4865, 0x6C6C, 0x6F40,0x5347, 0x7200};

        StringValue val = StringValue.of("Hello@SGr");
        int[] modbusRes = val.toModbusRegister(modbusDataTypeString);
        assertArrayEquals(modbusVal, modbusRes);

        Value resVal = Value.fromModbusRegister(modbusDataTypeString, modbusRes);
        assertInstanceOf(StringValue.class, resVal);
        assertEquals("Hello@SGr", resVal.getString());
    }

    private StringValue value;

    private Stream<Fixture> getConversions() {

        List<Fixture> fixtureList = new ArrayList<>();
        fixtureList.add(new Fixture("3.14159", 3.14159f, () -> value.getFloat32()));
        fixtureList.add(new Fixture("3.14159e10", 3.14159e10d, () -> value.getFloat64()));
        fixtureList.add(new Fixture("-9",    (byte)-9,   () -> value.getInt8()));
        fixtureList.add(new Fixture("17",    (short)17,  () -> value.getInt8U()));
        fixtureList.add(new Fixture("125",   (short)125, () -> value.getInt16()));
        fixtureList.add(new Fixture("244",   244,   () -> value.getInt16U()));
        fixtureList.add(new Fixture("44000", 44000, () -> value.getInt32()));
        fixtureList.add(new Fixture("88000", 88000L,   () -> value.getInt32U()));
        fixtureList.add(new Fixture("55544000", 55544000L, () -> value.getInt64()));
        fixtureList.add(new Fixture("6688000", new BigInteger("6688000"),   () -> value.getInt64U()));
        return fixtureList.stream();
    }

    @ParameterizedTest
    @MethodSource("getConversions")
    void convertToNumber(Fixture fixture) {

        value = StringValue.of(fixture.strValue);
        Number resVal = fixture.getFunction.get();
        assertEquals(fixture.expectedValue, resVal);
    }

    private Stream<Fixture> scaleDowns() {
        List<Fixture> fixtureList = new ArrayList<>();
        fixtureList.add(new Fixture("1000", 0.5f, () -> value.getFloat32()));
        fixtureList.add(new Fixture("2000", 1.0d, () -> value.getFloat64()));
        return fixtureList.stream();
    }

    @ParameterizedTest
    @MethodSource("scaleDowns")
    void scaleDown(Fixture fixture) {

        value = StringValue.of(fixture.strValue);
        value.scaleDown(2, 3);
        Number resVal = fixture.getFunction.get();
        assertEquals(fixture.expectedValue, resVal);

    }

    private Stream<Fixture> scaleUps() {
        List<Fixture> fixtureList = new ArrayList<>();
        fixtureList.add(new Fixture("1000", 2000000.0f, () -> value.getFloat32()));
        fixtureList.add(new Fixture("2000", 4000000.0d, () -> value.getFloat64()));
        return fixtureList.stream();
    }

    @ParameterizedTest
    @MethodSource("scaleUps")
    void scaleUp(Fixture fixture) {

        value = StringValue.of(fixture.strValue);
        value.scaleUp(2, 3);
        Number resVal = fixture.getFunction.get();
        assertEquals(fixture.expectedValue, resVal);
    }

    @Test
    void absValue() {
        value = StringValue.of("-3.14159");
        value.absValue();
        assertEquals(3.14159f, value.getFloat32());
    }

    @Test
    void jsonConversion() {
        // empty
        value = StringValue.of("");
        assertEquals(TextNode.valueOf(""), value.getJson());

        // null
        value = StringValue.of("null");
        assertEquals(NullNode.getInstance(), value.getJson());

        // plain text
        value = StringValue.of("someText");
        assertEquals(TextNode.valueOf("someText"), value.getJson());

        // object
        final ObjectNode objNode1 = new ObjectNode(JsonNodeFactory.instance);
        objNode1.put("key", "value");
        value = StringValue.of("{\"key\":\"value\"}");
        assertEquals(objNode1, value.getJson());

        // array
        final ArrayNode arrNode1 = new ArrayNode(JsonNodeFactory.instance);
        arrNode1.add(TextNode.valueOf("val1"));
        arrNode1.add(TextNode.valueOf("val2"));
        value = StringValue.of("[\"val1\",\"val2\"]");
        assertEquals(arrNode1, value.getJson());
    }
}
