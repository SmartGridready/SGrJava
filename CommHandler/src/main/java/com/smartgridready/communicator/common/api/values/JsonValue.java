package com.smartgridready.communicator.common.api.values;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigInteger;
import java.util.Map;

public class JsonValue extends Value {

    private JsonNode value;

    private JsonValue(JsonNode value) {
        this.value = value;
    }

    @Override
    public byte getInt8() {
        if (value.isNumber()) {
            long val = value.longValue();
            checkInt8(val);
            return (byte) val;
        }
        throw new UnsupportedOperationException("Cannot convert from JSON to int8.");
    }

    @Override
    public short getInt8U() {
        if (value.isNumber()) {
            long val = value.longValue();
            checkInt8U(val);
            return (short) val;
        }
        throw new UnsupportedOperationException("Cannot convert from JSON to int8U.");
    }

    @Override
    public short getInt16() {
        if (value.isNumber()) {
            long val = value.longValue();
            checkInt16(val);
            return (short) val;
        }
        throw new UnsupportedOperationException("Cannot convert from JSON to int16.");
    }

    @Override
    public int getInt16U() {
        if (value.isNumber()) {
            long val = value.longValue();
            checkInt16U(val);
            return (int) val;
        }
        throw new UnsupportedOperationException("Cannot convert from JSON to int16U.");
    }

    @Override
    public int getInt32() {
        if (value.isNumber()) {
            long val = value.longValue();
            checkInt32(val);
            return (int) val;
        }
        throw new UnsupportedOperationException("Cannot convert from JSON to int32.");
    }

    @Override
    public long getInt32U() {
        if (value.isNumber()) {
            long val = value.longValue();
            checkInt32U(val);
            return val;
        }
        throw new UnsupportedOperationException("Cannot convert from JSON to int32U.");
    }

    @Override
    public long getInt64() {
        if (value.isNumber()) {
            BigInteger val = value.bigIntegerValue();
            checkInt64(val);
            return val.longValue();
        }
        throw new UnsupportedOperationException("Cannot convert from JSON to int64.");
    }

    @Override
    public BigInteger getInt64U() {
        if (value.isNumber()) {
            BigInteger val = value.bigIntegerValue();
            checkInt64U(val);
            return val;
        } 
        throw new UnsupportedOperationException("Cannot convert from JSON to int64U.");
    }

    @Override
    public float getFloat32() {
        if (value.isNumber()) {
            double val = value.doubleValue();
            checkFloat32(val);
            return (float) val;
        }
        throw new UnsupportedOperationException("Cannot convert from JSON to float32.");
    }

    @Override
    public double getFloat64() {
        if (value.isNumber()) {
            double val = value.doubleValue();
            checkFloat32(val);
            return val;
        }
        throw new UnsupportedOperationException("Cannot convert from JSON to float64.");
    }

    @Override
    public String getString() {
        if (value.isValueNode()) {
            // return JSON string without quotes
            return value.asText();
        }
        return value.toString();
    }

    @Override
    public boolean getBoolean() {
        if (value.isBoolean()) {
            return value.booleanValue();
        } else if (value.isNumber()) {
            return value.longValue() != 0;
        }
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
    public JsonNode getJson() {
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
        return value.asText();
    }

    public static JsonValue of(JsonNode value) {
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
