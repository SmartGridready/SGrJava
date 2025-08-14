package com.smartgridready.communicator.common.api.values;

import com.smartgridready.ns.v0.DataTypeProduct;
import com.smartgridready.ns.v0.EnumMapProduct;
import com.smartgridready.ns.v0.ModbusBoolean;
import com.smartgridready.ns.v0.ModbusDataType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgridready.communicator.common.helper.JsonHelper;
import com.smartgridready.communicator.modbus.helper.ConversionHelper;
import com.smartgridready.driver.api.common.GenDriverException;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

/**
 * The base class for all SGr value types.
 */
public abstract class Value  {
    public static final BigInteger UNSIGNED_LONG_MASK = BigInteger.ONE.shiftLeft(Long.SIZE).subtract(BigInteger.ONE);

    /**
     * Gets the value as 8-bit integer.
     * @return a byte
     */
    public abstract byte getInt8();

    /** 
     * Gets the value as unsigned 8-bit integer.
     * @return a short
     */
    public abstract short getInt8U();

    /** 
     * Gets the value as 16-bit integer.
     * @return a short
     */
    public abstract short getInt16();

    /**
     * Gets the value as unsigned 16-bit integer.
     * @return an int
     */
    public abstract int getInt16U();

    /**
     * Gets the value as 32-bit integer.
     * @return an int
     */
    public abstract int getInt32();

    /**
     * Gets the value as unsigned 32-bit integer.
     * @return a long
     */
    public abstract long getInt32U();

    /**
     * Gets the value as 64-bit integer.
     * @return a long
     */
    public abstract long getInt64();

    /**
     * Gets the value as unsigned 64-bit integer.
     * @return a BigInteger
     */
    public abstract BigInteger getInt64U();

    /**
     * Gets the value as 32-bit floating-point.
     * @return a float
     */
    public abstract float getFloat32();

    /**
     * Gets the value as 64-bit floating-point.
     * @return a double
     */
    public abstract double getFloat64();

    /**
     * Gets the value as string.
     * @return a string
     */
    public abstract String getString();

    /**
     * Gets the value as boolean.
     * @return a boolean
     */
    public abstract boolean getBoolean();

    /**
     * Gets the value as SGr enumeration record.
     * @return an EnumRecord
     */
    public abstract EnumRecord getEnum();

    /**
     * Gets the value as SGr bitmap.
     * @return a map of strings to boolean values
     */
    public abstract Map<String, Boolean> getBitmap();

    /**
     * Gets the value as time stamp.
     * @return an Instant
     */
    public abstract Instant getDateTime();

    /**
     * Gets the value as JSON.
     * @return a JsonNode
     */
    public abstract JsonNode getJson();

    /**
     * Sets the instance's value to its absolute value, if possible.
     */
    public abstract void absValue();

    /**
     * Rounds the instance's value to the nearest integer, if possible.
     */
    public abstract void roundToInt();

    /**
     * Gets the value as array.
     * @return an array of {@link Value}
     */
    public abstract Value[] asArray();

    /**
     * Gets the value as JSON and converts to an instance of a given class.
     * @param <T> the generic type
     * @param aClass the class to convert to
     * @return an instance of the given class
     */
    public <T> T getJson(Class<T> aClass) {
        JsonNode node = getJson();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.treeToValue(node, aClass);
        } catch (JsonProcessingException e) {
            var msg = String.format("Unable to map JSON node to the given class '%s'", aClass.getSimpleName());
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Converts the value to a list of Modbus registers.
     * @param modbusDataType the Modbus data type to convert to
     * @return an array of integer
     */
    public int[] toModbusRegister(ModbusDataType modbusDataType) {

        if (modbusDataType.getFloat64() != null) {
            return ConversionHelper.doubleToRegisters(getFloat64());
        }
        if (modbusDataType.getFloat32() != null) {
            return ConversionHelper.floatToRegisters(getFloat32());
        }
        if (modbusDataType.getInt64() != null) {
            return ConversionHelper.longToRegisters(getInt64());
        }
        if (modbusDataType.getInt64U() != null) {
            return ConversionHelper.unsignedLongToRegister(getInt64U());
        }
        if (modbusDataType.getInt32() != null) {
            return ConversionHelper.intToRegisters(getInt32());
        }
        if (modbusDataType.getInt32U() != null) {
            return ConversionHelper.uintToRegisters(getInt32U());
        }
        if (modbusDataType.getInt16() != null) {
            return ConversionHelper.shortToRegister(getInt16());
        }
        if (modbusDataType.getInt16U() != null) {
            return ConversionHelper.shortToRegister((short) getInt16U());
        }
        if (modbusDataType.getInt8() != null) {
            return ConversionHelper.shortToRegister(getInt8());
        }
        if (modbusDataType.getInt8U() != null) {
            return ConversionHelper.shortToRegister(getInt8U());
        }
        if (modbusDataType.getString() != null) {
            return ConversionHelper.convStringToRegisters(getString());
        }
        if (modbusDataType.getBoolean() != null) {
            short booleanAsShort = mapBooleanToShort(modbusDataType.getBoolean(), getBoolean());
            return ConversionHelper.shortToRegister(booleanAsShort);
        }
        if (modbusDataType.getDateTime() != null) {
            return ConversionHelper.longToRegisters(getInt64());
        }

        throw new IllegalArgumentException(
                String.format("Conversion to modbus register for type %s not supported.",
                        DataType.getModbusDataTypeName(modbusDataType)));
    }

    /**
     * Converts the value to a list of Modbus discrete inputs.
     * @param modbusDataType the Modbus data type to convert to
     * @return an array of byte
     */
    public byte[] toModbusDiscreteVal(ModbusDataType modbusDataType) {
        if (modbusDataType.getBoolean() != null) {
            return new byte[]{getBoolean() ? (byte)1 : (byte)0};
        }
        throw new IllegalArgumentException(
                String.format("Conversion to modbus discrete value for type %s not supported.",
                        DataType.getModbusDataTypeName(modbusDataType)));
    }

    /**
     * Converts a list of Modbus registers to a given value.
     * @param modbusDataType the Modbus data type to convert to
     * @param registers the Modbus registers
     * @return an instance of {@link Value}
     */
    public static Value fromModbusRegister(ModbusDataType modbusDataType, int[] registers) {

        if (modbusDataType.getFloat64() != null) {
            return Float64Value.of(ConversionHelper.byteBufFromRegisters(registers).getDouble());
        }
        if (modbusDataType.getFloat32() != null) {
            return Float32Value.of(ConversionHelper.byteBufFromRegisters(registers).getFloat());
        }
        if (modbusDataType.getInt64() != null) {
            return Int64Value.of(ConversionHelper.byteBufFromRegisters(registers).getLong());
        }
        if (modbusDataType.getInt64U() != null) {
            long signedLong = ConversionHelper.byteBufFromRegisters(registers).getLong();
            return Int64UValue.of(BigInteger.valueOf(signedLong).and(UNSIGNED_LONG_MASK));
        }
        if (modbusDataType.getInt32() != null) {
            return Int32Value.of(ConversionHelper.byteBufFromRegisters(registers).getInt());
        }
        if (modbusDataType.getInt32U() != null) {
            return Int32UValue.of(Integer.toUnsignedLong(ConversionHelper.byteBufFromRegisters(registers).getInt()));
        }
        if (modbusDataType.getInt16() != null) {
            return Int16Value.of(ConversionHelper.byteBufFromRegisters(registers).getShort());
        }
        if (modbusDataType.getInt16U() != null) {
            return Int16UValue.of(ConversionHelper.byteBufFromRegisters(registers).getShort() & 0xFFFF);
        }
        if (modbusDataType.getInt8() != null) {
            return Int8Value.of((byte)ConversionHelper.byteBufFromRegisters(registers).getShort());
        }
        if (modbusDataType.getInt8U() != null) {
            return Int8UValue.of(ConversionHelper.byteBufFromRegisters(registers).getShort());
        }
        if (modbusDataType.getString() != null) {
            return StringValue.of(ConversionHelper.convRegistersToString(registers, 0, registers.length * 2));
        }
        if (modbusDataType.getBoolean() != null) {
            short booleanAsShort = ConversionHelper.byteBufFromRegisters(registers).getShort();
            return BooleanValue.of(mapShortToBoolean(modbusDataType.getBoolean(), booleanAsShort));
        }
        if (modbusDataType.getDateTime() != null) {
            return DateTimeValue.of(Instant.ofEpochMilli(ConversionHelper.byteBufFromRegisters(registers).getLong()));
        }

        throw new IllegalArgumentException(String.format("Modbus register type %s to Value.class conversion from register not supported.",
                DataType.getModbusDataTypeName(modbusDataType)));
    }

    /**
     * Converts a string to a different to a {@link Value}.
     * @param dataType the data type to convert to
     * @param value the string value to convert
     * @return an instance of {@link Value}
     */
    public static Value fromString(DataTypeProduct dataType, String value) {

        if (dataType.getFloat64() != null) {
            return Float64Value.of(Double.parseDouble(value));
        }
        if (dataType.getFloat32() != null) {
            return Float32Value.of(Float.parseFloat(value));
        }
        if (dataType.getInt64() != null) {
            return Int64Value.of(Long.parseLong(value));
        }
        if (dataType.getInt64U() != null) {
            long signedLong = Long.parseLong(value);
            return Int64UValue.of(BigInteger.valueOf(signedLong).and(UNSIGNED_LONG_MASK));
        }
        if (dataType.getInt32() != null) {
            return Int32Value.of(Integer.parseInt(value));
        }
        if (dataType.getInt32U() != null) {
            return Int32UValue.of(Long.parseLong(value));
        }
        if (dataType.getInt16() != null) {
            return Int16Value.of(Short.parseShort(value));
        }
        if (dataType.getInt16U() != null) {
            return Int16UValue.of(Integer.parseInt(value));
        }
        if (dataType.getInt8() != null) {
            return Int8Value.of((byte)Short.parseShort(value));
        }
        if (dataType.getInt8U() != null) {
            return Int8UValue.of(Short.parseShort(value));
        }
        if (dataType.getString() != null) {
            return StringValue.of(value);
        }
        if (dataType.getBoolean() != null) {
            return BooleanValue.of(Boolean.parseBoolean(value));
        }
        if (dataType.getDateTime() != null) {
            return DateTimeValue.of(Instant.parse(value));
        }
        if (dataType.getJson() != null) {
            try {
                return JsonHelper.parseJsonResponse(null, value);
            } catch (GenDriverException e) {
                throw new IllegalArgumentException("Could not convert string to JSON", e);
            }
        }

        throw new IllegalArgumentException(String.format("Generic type %s conversion from String to Value.class conversion from register not supported.",
                DataType.getGenDataTypeName(dataType)));
    }

    /**
     * Gets the ordinal value of an enumeration.
     * @param enumMapProduct the enumeration definition
     * @return a 64-bit integer value
     */
    public Int64Value enumToOrdinalValue(EnumMapProduct enumMapProduct) {
        return Int64Value.of(getInt64());
    }

    /**
     * Gets a value from a Modbus discrete input.
     * @param modbusDataType the Modbus data type
     * @param bitregister the discrete input valueü
     * @return an instance of {@link Value}
     */
    public static Value fromDiscreteInput(ModbusDataType modbusDataType, boolean[] bitregister) {
        if (modbusDataType.getBoolean() != null) {
            return BooleanValue.of(bitregister[0]);
        }

        throw new IllegalArgumentException(String.format("Modbus type %s to Value.class conversion from discrete input not supported.",
                DataType.getModbusDataTypeName(modbusDataType)));
    }

    static void checkInt8(long value) {
        if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("Cannot convert value %d to int8. Value out of range", value));
        }
    }

    static void checkInt8U(long value) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException(String.format("Cannot convert value %d to int8U. Value out of range", value));
        }
    }

    static void checkInt16(long value) {
        if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("Cannot convert value %d to int16. Value out of range", value));
        }
    }

    static void checkInt16U(long value) {
        if (value < 0 || value > 65535) {
            throw new IllegalArgumentException(String.format("Cannot convert value %d to int16U. Value out of range", value));
        }
    }

    static void checkInt32(long value) {
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("Cannot convert value %d to int32. Value out of range", value));
        }
    }

    static void checkInt32U(long value) {
        if (value < 0 || value > 4294967295L) {
            throw new IllegalArgumentException(String.format("Cannot convert value %d to int32U. Value out of range", value));
        }
    }

    static void checkInt64(BigInteger value) {
        if (value.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0  || value.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
            throw new IllegalArgumentException(String.format("Cannot convert value %d to int64. Value out of range", value));
        }
    }

    static void checkInt64U(BigInteger value) {
        if (value.compareTo(BigInteger.ZERO) < 0  || value.compareTo(new BigInteger("18446744073709551615")) > 0) {
            throw new IllegalArgumentException(String.format("Cannot convert value %f to int64. Value out of range", value.doubleValue()));
        }
    }

    static void checkFloat32(double value) {
        if (value < -Float.MAX_VALUE || value > Float.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("Cannot convert value %f to float32. Value out of range", value));
        }
    }

    private static short mapBooleanToShort(ModbusBoolean modbusBoolean, boolean value) {

        if (modbusBoolean.getFalseValue() != null) {
            // mapping of false value is defined
            return value ? 0 : modbusBoolean.getFalseValue().shortValue();
        }
        if (modbusBoolean.getTrueValue() != null) {
            // mapping of true value is defined
            return value ? modbusBoolean.getTrueValue().shortValue() : 0;
        }
        // no mapping defined
        return value ? (short)1 : (short)0;
    }

    private static boolean mapShortToBoolean(ModbusBoolean modbusBoolean, short value) {

        if (modbusBoolean.getFalseValue() != null) {
            // check if mapped value for false matches the value received from modbus
            return value != modbusBoolean.getFalseValue().shortValue();
        }
        if ((modbusBoolean.getTrueValue() != null) && (value == modbusBoolean.getTrueValue().shortValue())) {
            // check mapped value for true matches the value received from modbus
            return value == modbusBoolean.getTrueValue().shortValue();
        }
        // no mapping defined
        return value != 0;
    }
}
