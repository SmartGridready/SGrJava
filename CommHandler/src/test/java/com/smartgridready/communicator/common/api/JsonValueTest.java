package com.smartgridready.communicator.common.api;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.smartgridready.communicator.common.api.values.JsonValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.ns.v0.EmptyType;
import com.smartgridready.ns.v0.ModbusBoolean;
import com.smartgridready.ns.v0.ModbusDataType;

class JsonValueTest {

    private static final ModbusDataType modbusDataTypeBoolean = new ModbusDataType();
    private static final ModbusDataType modbusDataTypeInt32 = new ModbusDataType();
    private static final ModbusDataType modbusDataTypeString = new ModbusDataType();
    static {
        modbusDataTypeString.setString(new EmptyType());
        modbusDataTypeInt32.setInt32(new EmptyType());
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
        objNode1.put("prop_long", 1234567890L);
        objNode1.put("prop_bool", false);
        value = JsonValue.of(objNode1);
        assertEquals("{\"prop_long\":1234567890,\"prop_bool\":false}", value.getString());

        // array
        final ArrayNode arrNode1 = new ArrayNode(JsonNodeFactory.instance);
        arrNode1.add(TextNode.valueOf("abc"));
        arrNode1.add(TextNode.valueOf("def"));
        value = JsonValue.of(arrNode1);
        assertEquals("[\"abc\",\"def\"]", value.getString());
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

        // int32
        value = JsonValue.of(IntNode.valueOf(1));
        assertArrayEquals(new int[]{0x0,0x1}, value.toModbusRegister(modbusDataTypeInt32));

        // string
        value = JsonValue.of(TextNode.valueOf("abc"));
        assertArrayEquals(new int[]{0x6162,0x6300}, value.toModbusRegister(modbusDataTypeString));
    }
}
