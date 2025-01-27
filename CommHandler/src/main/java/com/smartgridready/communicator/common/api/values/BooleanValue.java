package com.smartgridready.communicator.common.api.values;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.BooleanNode;

import java.math.BigInteger;
import java.util.Map;

public class BooleanValue extends Value {

    private final boolean value;

    private BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public byte getInt8() {
        return value ? (byte)1 : (byte)0;
    }

    @Override
    public short getInt8U() {
        return value ? (short) 1 : (short)0;
    }

    @Override
    public short getInt16() {
        return value ? (short) 1 : (short)0;
    }

    @Override
    public int getInt16U() {
        return value ? 1 : 0;
    }

    @Override
    public int getInt32() {
        return value ? 1 : 0;
    }

    @Override
    public long getInt32U() {
        return value ? (long)1 : (long)0;
    }

    @Override
    public long getInt64() {
        return value ? (long)1 : (long)0;
    }

    @Override
    public BigInteger getInt64U() {
        return value ? BigInteger.ONE : BigInteger.ZERO;
    }

    @Override
    public float getFloat32() {
        return value ? 1 : 0;
    }

    @Override
    public double getFloat64() {
        return value ? 1 : 0;
    }

    @Override
    public String getString() {
        return value ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
    }

    @Override
    public EnumRecord getEnum() {
        throw new UnsupportedOperationException("Cannot convert from boolean to enum.");
    }

    @Override
    public Map<String, Boolean> getBitmap() {
        throw new UnsupportedOperationException("Cannot convert from boolean to bitmap.");
    }

    @Override
    public boolean getBoolean() {
        return value;
    }

    @Override
    public JsonNode getJson() {
        return BooleanNode.valueOf(value);
    }

    @Override
    public void absValue() {
        // no absolute value for boolean
    }

    @Override
    public void roundToInt() {
        // no rounding for int
    }

    public static BooleanValue of(boolean value) {
        return new BooleanValue(value);
    }

    @Override
    public BooleanValue[] asArray() {
        return new BooleanValue[]{this};
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;

        if (o==null || getClass()!=o.getClass()) return false;

        BooleanValue that = (BooleanValue) o;

        return new EqualsBuilder().append(value, that.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }
}
