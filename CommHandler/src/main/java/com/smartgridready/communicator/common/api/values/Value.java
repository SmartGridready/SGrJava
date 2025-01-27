package com.smartgridready.communicator.common.api.values;

import com.smartgridready.ns.v0.DataTypeProduct;
import com.smartgridready.ns.v0.EnumMapProduct;
import com.smartgridready.ns.v0.ModbusBoolean;
import com.smartgridready.ns.v0.ModbusDataType;
import com.smartgridready.communicator.modbus.helper.ConversionHelper;

import java.math.BigInteger;
import java.util.Map;

public abstract class Value  {
    public static final BigInteger UNSIGNED_LONG_MASK = BigInteger.ONE.shiftLeft(Long.SIZE).subtract(BigInteger.ONE);
    public abstract byte getInt8();
    public abstract short getInt8U();
    public abstract short getInt16();
    public abstract int getInt16U();
    public abstract int getInt32();
    public abstract long getInt32U();
    public abstract long getInt64();
    public abstract BigInteger getInt64U();
    public abstract float getFloat32();
    public abstract double getFloat64();
    public abstract String getString();
    public abstract boolean getBoolean();
    public abstract EnumRecord getEnum();
    public abstract Map<String, Boolean> getBitmap();
    public abstract String getJson();
    public abstract void absValue();
    public abstract void roundToInt();

    public abstract Value[] asArray();

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

        throw new IllegalArgumentException(
                String.format("Conversion to modbus register for type %s not supported.",
                        DataType.getModbusDataTypeName(modbusDataType)));
    }

    public byte[] toModbusDiscreteVal(ModbusDataType modbusDataType) {
        if (modbusDataType.getBoolean() != null) {
            return new byte[]{getBoolean() ? (byte)1 : (byte)0};
        }
        throw new IllegalArgumentException(
                String.format("Conversion to modbus discrete value for type %s not supported.",
                        DataType.getModbusDataTypeName(modbusDataType)));
    }


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

        throw new IllegalArgumentException(String.format("Modbus register type %s to Value.class conversion from register not supported.",
                DataType.getModbusDataTypeName(modbusDataType)));
    }

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
        if (dataType.getJson() != null) {
            return JsonValue.of(value);
        }

        throw new IllegalArgumentException(String.format("Generic type %s conversion from String to Value.class conversion from register not supported.",
                DataType.getGenDataTypeName(dataType)));

    }

    @SuppressWarnings("unused")
    public Int64Value enumToOrdinalValue (EnumMapProduct enumMapProduct) {
        return Int64Value.of(getInt64());
    }

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
