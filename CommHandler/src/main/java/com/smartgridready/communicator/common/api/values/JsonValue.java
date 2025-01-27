package com.smartgridready.communicator.common.api.values;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigInteger;
import java.util.Map;

public class JsonValue extends Value {

    private String value;

    private JsonValue(String value) {
        this.value = value;
    }

    @Override
    public byte getInt8() {
        throw new UnsupportedOperationException("Cannot convert from JSON to int8.");
    }

    @Override
    public short getInt8U() {
        throw new UnsupportedOperationException("Cannot convert from JSON to int8U.");
    }

    @Override
    public short getInt16() {
        throw new UnsupportedOperationException("Cannot convert from JSON to int16.");
    }

    @Override
    public int getInt16U() {
        throw new UnsupportedOperationException("Cannot convert from JSON to int16U.");
    }

    @Override
    public int getInt32() {
        throw new UnsupportedOperationException("Cannot convert from JSON to int32.");
    }

    @Override
    public long getInt32U() {
        throw new UnsupportedOperationException("Cannot convert from JSON to int32U.");
    }

    @Override
    public long getInt64() {
        throw new UnsupportedOperationException("Cannot convert from JSON to int64.");
    }

    @Override
    public BigInteger getInt64U() {
        throw new UnsupportedOperationException("Cannot convert from JSON to int64U.");
    }

    @Override
    public float getFloat32() {
        throw new UnsupportedOperationException("Cannot convert from JSON to float32.");
    }

    @Override
    public double getFloat64() {
        throw new UnsupportedOperationException("Cannot convert from JSON to float64.");
    }

    @Override
    public String getString() {
        return value;
    }

    @Override
    public boolean getBoolean() {
        throw new UnsupportedOperationException("Cannot convert from JSON to boolean.");
    }

    @Override
    public EnumRecord getEnum() {
        throw new UnsupportedOperationException("Cannot convert from JSON to enum.");
    }

    @Override
    public Map<String, Boolean> getBitmap() {
        throw new UnsupportedOperationException("Cannot convert from JSON to bitmap.");
    }

    @Override
    public String getJson() {
        return value;
    }

    @Override
    public JsonValue[] asArray() {
        return new JsonValue[]{this};
    }

    @Override
    public void absValue() {
        // not required
    }

    @Override
    public void roundToInt() {
        // not required
    }

    @Override
    public String toString() {
        return value;
    }

    public static JsonValue of(String value) {
        return new JsonValue(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;

        if (o==null || getClass()!=o.getClass()) return false;

        JsonValue that = (JsonValue) o;

        return new EqualsBuilder().append(value, that.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }
}
