package com.smartgridready.communicator.common.api.values;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;

/**
 * Implements an SGr value containing a 64-bit unsigned integer value.
 * Requires {@link BigInteger}, because Java has no matching numeric type.
 */
public class Int64UValue extends Value {

    private BigInteger value;

    private Int64UValue(BigInteger value) {
        this.value = value;
    }

    @Override
    public byte getInt8() {
        Value.checkInt8(value.longValue());
        return (byte)value.longValue();
    }

    @Override
    public short getInt8U() {
        Value.checkInt8U(value.longValue());
        return (short)value.longValue();
    }

    @Override
    public short getInt16() {
        Value.checkInt16(value.longValue());
        return (short)value.longValue();
    }

    @Override
    public int getInt16U() {
        Value.checkInt16U(value.longValue());
        return (int)value.longValue();
    }

    @Override
    public int getInt32() {
        Value.checkInt32(value.longValue());
        return (int)value.longValue();
    }

    @Override
    public long getInt32U() {
        Value.checkInt32U(value.longValue());
        return (int)value.longValue();
    }

    @Override
    public long getInt64() {
        Value.checkInt64(value);
        return value.longValue();
    }

    @Override
    public BigInteger getInt64U() {
        Value.checkInt64U(value);
        return value;
    }

    @Override
    public float getFloat32() {
        Value.checkFloat32(value.doubleValue());
        return value.floatValue();
    }

    @Override
    public double getFloat64() {
        return value.doubleValue();
    }

    @Override
    public String getString() {
        return value.toString();
    }

    @Override
    public boolean getBoolean() {
        return value.compareTo(BigInteger.ZERO) != 0;
    }

    @Override
    public EnumRecord getEnum() {
        throw new UnsupportedOperationException("Cannot convert from int64U value to a enum value.");
    }

    @Override
    public Map<String, Boolean> getBitmap() {
        throw new UnsupportedOperationException("Cannot convert from int64U value to a bitmap value.");
    }

    @Override
    public Instant getDateTime() {
        return Instant.ofEpochMilli(getInt64());
    }

    @Override
    public JsonNode getJson() {
        return BigIntegerNode.valueOf(value);
    }

    @Override
    public Int64UValue[] asArray() {
        return new Int64UValue[]{this};
    }

    /**
     * Scales down by powers of 10.
     * @param mul the multiplicator
     * @param powOf10 the exponent of the power of 10
     * @return a numeric value
     */
    public NumberValue<Double> scaleDown(int mul, int powOf10) {
        if (mul != 1 || powOf10 !=0) {
            BigInteger val = value.divide(BigInteger.valueOf(mul));
            val = val.divide(BigInteger.valueOf((long) Math.pow(10.0, powOf10)));
            Value.checkInt64U(val);
            return Float64Value.of(val.doubleValue());
        }
        return Float64Value.of(value.doubleValue());
    }

    /**
     * Scales up by powers of 10.
     * @param mul the multiplicator
     * @param powOf10 the exponent of the power of 10
     * @return a numeric value
     */
    public NumberValue<Double> scaleUp(int mul, int powOf10) {
        if (mul != 1 || powOf10 !=0) {
            BigInteger val = value.multiply(BigInteger.valueOf(mul));
            val = val.multiply(BigInteger.valueOf((long) Math.pow(10.0, powOf10)));
            Value.checkInt64U(val);
            return Float64Value.of(val.doubleValue());
        }
        return Float64Value.of(value.doubleValue());
    }

    @Override
    public void absValue() {
        value = value.abs();
    }

    @Override
    public void roundToInt() {
        // is already int
    }

    /**
     * Creates a new instance from BigInteger.
     * @param value the BigInteger value
     * @return an instance of {@link Int64UValue}
     */
    public static Int64UValue of(BigInteger value) {
        return new Int64UValue(value);
    }
}
