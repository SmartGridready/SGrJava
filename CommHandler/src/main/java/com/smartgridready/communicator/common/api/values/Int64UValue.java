package com.smartgridready.communicator.common.api.values;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BigIntegerNode;

public class Int64UValue extends Value {

    BigInteger value;

    public Int64UValue(BigInteger value) {
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

    @Override
    public Int64UValue[] asArray() {
        return new Int64UValue[]{this};
    }

    public NumberValue<Double> scaleDown(int mul, int powOf10) {
        if (mul != 1 || powOf10 !=0) {
            BigInteger val = value.divide(BigInteger.valueOf(mul));
            val = val.divide(BigInteger.valueOf((long) Math.pow(10.0, powOf10)));
            Value.checkInt64U(val);
            return Float64Value.of(val.doubleValue());
        }
        return Float64Value.of(value.doubleValue());
    }

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

    public static Int64UValue of(BigInteger value) {
        return new Int64UValue(value);
    }
}
