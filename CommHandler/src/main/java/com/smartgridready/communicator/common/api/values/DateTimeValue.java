package com.smartgridready.communicator.common.api.values;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

public class DateTimeValue extends Value {

    private Instant value;

    private DateTimeValue(Instant value) {
        this.value = value;
    }

    @Override
    public byte getInt8() {
        long val = getInt64();
        Value.checkInt8(val);
        return (byte) val;
    }

    @Override
    public short getInt8U() {
        long val = getInt64();
        Value.checkInt8U(val);
        return (short) val;
    }

    @Override
    public short getInt16() {
        long val = getInt64();
        Value.checkInt16(val);
        return (short) val;
    }

    @Override
    public int getInt16U() {
        long val = getInt64();
        Value.checkInt16U(val);
        return (int) val;
    }

    @Override
    public int getInt32() {
        long val = getInt64();
        Value.checkInt32(val);
        return (int) val;
    }

    @Override
    public long getInt32U() {
        long val = getInt64();
        Value.checkInt32U(val);
        return val;
    }

    @Override
    public long getInt64() {
        return value.toEpochMilli();
    }

    @Override
    public BigInteger getInt64U() {
        return BigInteger.valueOf(getInt64());
    }

    @Override
    public float getFloat32() {
        return getInt64();
    }

    @Override
    public double getFloat64() {
        return getInt64();
    }

    @Override
    public String getString() {
        return value.toString();
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
        return getInt64() != 0L;
    }

    @Override
    public Instant getDateTime() {
        return value;
    }

    @Override
    public JsonNode getJson() {
        return TextNode.valueOf(value.toString());
    }

    @Override
    public void absValue() {
        value = Instant.ofEpochMilli(Math.abs(getInt64()));
    }

    @Override
    public void roundToInt() {
        // is already an int
    }

    public static DateTimeValue of(Instant value) {
        return new DateTimeValue(value);
    }

    public static DateTimeValue of(String dateTimeStr) {
        return new DateTimeValue(Instant.parse(dateTimeStr));
    }

    public static DateTimeValue of(long epochMillis) {
        return new DateTimeValue(Instant.ofEpochMilli(epochMillis));
    }

    @Override
    public DateTimeValue[] asArray() {
        return new DateTimeValue[]{this};
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;

        if (o==null || getClass()!=o.getClass()) return false;

        DateTimeValue that = (DateTimeValue) o;

        return new EqualsBuilder().append(value, that.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }
}
