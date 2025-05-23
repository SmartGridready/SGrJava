package com.smartgridready.communicator.common.api;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.FloatNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.LongNode;
import com.smartgridready.communicator.common.api.values.EnumRecord;
import com.smartgridready.communicator.common.api.values.JsonValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.ns.v0.EmptyType;
import com.smartgridready.ns.v0.ModbusBoolean;
import com.smartgridready.ns.v0.ModbusDataType;

class JsonValueTest {

    private static final ModbusDataType modbusDataTypeBoolean = new ModbusDataType();
    private static final ModbusDataType modbusDataTypeInt32 = new ModbusDataType();
    private static final ModbusDataType modbusDataTypeString = new ModbusDataType();
    private static final ModbusDataType modbusDataTypeFloat32 = new ModbusDataType();
    static {
        modbusDataTypeString.setString(new EmptyType());
        modbusDataTypeInt32.setInt32(new EmptyType());
        modbusDataTypeFloat32.setFloat32(new EmptyType());
        modbusDataTypeBoolean.setBoolean(new ModbusBoolean());
    }

    private Value value;

    @Test
    void numericConversion() {
        // boolean
        final boolean boolVal = false;
        value = JsonValue.of(BooleanNode.valueOf(boolVal));
        assertEquals(boolVal, value.getBoolean());

        // int32
        final int int32Val = 123456;
        value = JsonValue.of(IntNode.valueOf(int32Val));
        assertEquals(int32Val, value.getInt32());

        // float32
        final float float32Val = 123.456f;
        value = JsonValue.of(FloatNode.valueOf(float32Val));
        assertEquals(float32Val, value.getFloat32());
    }
    
    @Test
    void stringConversion() {
        // null
        value = JsonValue.of(NullNode.getInstance());
        assertEquals("null", value.getString());

        // string
        value = JsonValue.of(TextNode.valueOf("abc"));
        assertEquals("abc", value.getString());
        
        // boolean
        value = JsonValue.of(BooleanNode.valueOf(true));
        assertEquals("true", value.getString());

        // int32
        value = JsonValue.of(IntNode.valueOf(123456));
        assertEquals("123456", value.getString());

        // int64
        value = JsonValue.of(LongNode.valueOf(1234567890L));
        assertEquals("1234567890", value.getString());

        // float32
        value = JsonValue.of(FloatNode.valueOf(123.456f));
        assertEquals("123.456", value.getString());

        // object
        final ObjectNode objNode1 = new ObjectNode(JsonNodeFactory.instance);
        objNode1.put("propLong", 1234567890L);
        objNode1.put("propBool", false);
        value = JsonValue.of(objNode1);
        assertEquals("{\"propLong\":1234567890,\"propBool\":false}", value.getString());

        // convert object to class
        TestObject convObj = value.getJson(TestObject.class);
        assertEquals(1234567890L, convObj.getPropLong());
        assertEquals(false, convObj.getPropBool());

        // array
        final ArrayNode arrNode1 = new ArrayNode(JsonNodeFactory.instance);
        arrNode1.add(TextNode.valueOf("abc"));
        arrNode1.add(TextNode.valueOf("def"));
        value = JsonValue.of(arrNode1);
        assertEquals("[\"abc\",\"def\"]", value.getString());

        // convert array to class
        String[] convArr = value.getJson(String[].class);
        assertEquals(2, convArr.length);
    }

    @Test
    void dateTimeConversion() {
        final Instant ts = Instant.EPOCH;

        // string
        value = JsonValue.of(TextNode.valueOf(ts.toString()));
        assertEquals(ts, value.getDateTime());

        // numeric
        value = JsonValue.of(LongNode.valueOf(ts.toEpochMilli()));
        assertEquals(ts, value.getDateTime());
    }

    @Test
    void enumConversion() {
        final EnumRecord enumValue = new EnumRecord("ENUM_VALUE", null, null);

        // string
        value = JsonValue.of(TextNode.valueOf(enumValue.getLiteral()));
        assertEquals(enumValue, value.getEnum());
    }

    @Test
    void fromModbusConversion() {
        // boolean
        value = Value.fromDiscreteInput(modbusDataTypeBoolean, new boolean[]{true});
        assertEquals(BooleanNode.valueOf(true), value.getJson());

        // int32
        value = Value.fromModbusRegister(modbusDataTypeInt32, new int[]{0x0,0x1});
        assertEquals(LongNode.valueOf(1), value.getJson());
    }

    @Test
    void toModbusConversion() {
        // boolean
        value = JsonValue.of(BooleanNode.valueOf(true));
        assertArrayEquals(new byte[]{(byte)0x01}, value.toModbusDiscreteVal(modbusDataTypeBoolean));
        assertArrayEquals(new int[]{0x0000, 0x0001}, value.toModbusRegister(modbusDataTypeInt32));
        assertArrayEquals(new int[]{0x3f80, 0x0000}, value.toModbusRegister(modbusDataTypeFloat32));

        // int32
        value = JsonValue.of(IntNode.valueOf(1));
        assertArrayEquals(new int[]{0x0,0x1}, value.toModbusRegister(modbusDataTypeInt32));
        assertArrayEquals(new int[]{0x3f80, 0x0000}, value.toModbusRegister(modbusDataTypeFloat32));

        // string
        value = JsonValue.of(TextNode.valueOf("abc"));
        assertArrayEquals(new int[]{0x6162,0x6300}, value.toModbusRegister(modbusDataTypeString));
        value = JsonValue.of(TextNode.valueOf("1.0"));
        assertArrayEquals(new int[]{0x0000, 0x0001}, value.toModbusRegister(modbusDataTypeInt32));
        assertArrayEquals(new int[]{0x3f80, 0x0000}, value.toModbusRegister(modbusDataTypeFloat32));
    }

    @Test
    void create() {
        // object
        TestObject obj = TestObject.of(123456789L, true);
        Value value = JsonValue.of(obj);
        TestObject jobj = value.getJson(TestObject.class);
        assertEquals(obj, jobj);

        // string
        String jstr = "{\"propLong\":123456789,\"propBool\":true}";
        value = JsonValue.of(jstr);
        jobj = value.getJson(TestObject.class);
        assertEquals(obj, jobj);
    }

    @Test
    void roundToInt() {
        JsonValue value = JsonValue.of(FloatNode.valueOf(1.2345f));
        value.roundToInt();
        assertEquals(1L, value.getInt64());

        value = JsonValue.of(TextNode.valueOf("test"));
        value.roundToInt();
        assertEquals("test", value.getString());
    }

    @Test
    void absValue() {
        JsonValue value = JsonValue.of(FloatNode.valueOf(-1.2345f));
        value.absValue();
        assertEquals(1.2345f, value.getFloat32());

        value = JsonValue.of(LongNode.valueOf(-12345L));
        value.absValue();
        assertEquals(12345L, value.getInt64());

        value = JsonValue.of(TextNode.valueOf("test"));
        value.absValue();
        assertEquals("test", value.getString());
    }

    @Test
    void asArray() {
        final float[] floats = new float[] { 1.0f, 2.0f, 3.0f };
        JsonValue value = JsonValue.of("[1.0,2.0,3.0]");
        JsonValue[] arr = value.asArray();
        assertEquals(floats.length, arr.length);
        for (int i = 0; i < arr.length; i++) {
            assertEquals(floats[i], arr[i].getFloat32());
        }
    }

    @Test
    void asBitmap() {
        JsonValue value = JsonValue.of("{\"a\":true,\"b\":1,\"c\":false,\"d\":\"test\"}");
        Map<String, Boolean> bm = value.getBitmap();
        assertEquals(4, bm.size());
        assertEquals(true, bm.get("a"));
        assertEquals(true, bm.get("b"));
        assertEquals(false, bm.get("c"));
        assertEquals(false, bm.get("d"));
    }

    private static class TestObject {
        private Long propLong;
        private Boolean propBool;

        public Long getPropLong() {
            return propLong;
        }

        public void setPropLong(Long value) {
            propLong = value;
        }

        public Boolean getPropBool() {
            return propBool;
        }

        public void setPropBool(Boolean value) {
            propBool = value;
        }

        public static TestObject of(Long prop_long, Boolean prop_bool) {
            var obj = new TestObject();
            obj.setPropLong(prop_long);
            obj.setPropBool(prop_bool);
            return obj;
        }

        @Override
        public boolean equals(Object o) {
            if (this==o) return true;
            if (o==null || getClass()!=o.getClass()) return false;
            TestObject that = (TestObject) o;
            return new EqualsBuilder().append(propLong, that.propLong).append(propBool, that.propBool).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(propLong).append(propBool).toHashCode();
        }
    }
}
