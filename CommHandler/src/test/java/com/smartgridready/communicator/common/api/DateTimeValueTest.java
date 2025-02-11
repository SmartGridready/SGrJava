package com.smartgridready.communicator.common.api;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.smartgridready.communicator.common.api.values.DateTimeValue;
import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.ns.v0.EmptyType;
import com.smartgridready.ns.v0.ModbusDataType;

class DateTimeValueTest {

    private static final ModbusDataType modbusDataTypeDateTime = new ModbusDataType();
    static {
        modbusDataTypeDateTime.setDateTime(new EmptyType());
    }

    private Value value;

    @Test
    void numericConversion() {
        // boolean
        value = DateTimeValue.of(Instant.ofEpochMilli(1L));
        assertEquals(true, value.getBoolean());

        // int32
        value = DateTimeValue.of(Instant.ofEpochMilli(1L));
        assertEquals(1, value.getInt32());

        // float32
        value = DateTimeValue.of(Instant.ofEpochMilli(1L));
        assertEquals(1.0, value.getFloat32());
    }
    
    @Test
    void stringConversion() {
        value = DateTimeValue.of(Instant.ofEpochMilli(0L));
        assertEquals("1970-01-01T00:00:00Z", value.getString());

        value = StringValue.of("1970-01-01T00:00:01Z");
        assertEquals(Instant.ofEpochMilli(1000L), value.getDateTime());
    }

    @Test
    void fromModbusConversion() {
        value = Value.fromModbusRegister(modbusDataTypeDateTime, new int[]{0x0,0x0,0x0,0x1});
        assertEquals(Instant.ofEpochMilli(1L), value.getDateTime());
    }

    @Test
    void toModbusConversion() {
        value = DateTimeValue.of(Instant.ofEpochMilli(1L));
        assertArrayEquals(new int[]{0x0,0x0,0x0,0x1}, value.toModbusRegister(modbusDataTypeDateTime));
    }
}
