package com.smartgridready.communicator.common.api.values;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

public class JsonValue extends Value {

    private final JsonNode value;

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
        // stringify object
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
        if (value.isTextual()) {
            return new EnumRecord(value.textValue(), null, null);
        }
        throw new UnsupportedOperationException("Cannot convert from JSON object to enum.");
    }

    @Override
    public Map<String, Boolean> getBitmap() {
        throw new UnsupportedOperationException("Cannot convert from JSON to bitmap.");
    }

    @Override
    public Instant getDateTime() {
        if (value.isTextual()) {
            return Instant.parse(value.textValue());
        } else if (value.isNumber()) {
            return Instant.ofEpochMilli(value.longValue());
        }
        throw new UnsupportedOperationException("Cannot convert from JSON object to datetime.");
    }

    @Override
    public JsonNode getJson() {
        return value;
    }

    @Override
    public JsonValue[] asArray() {
        // cannot create actual JSON array here
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

    public static JsonValue of(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        return new JsonValue(objectMapper.valueToTree(object));
    }

    public static JsonValue of(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return new JsonValue(objectMapper.readTree(json));
        } catch (Exception e) {
            var msg = String.format("Unable to deserialize JSON string '%s'", json);
            throw new IllegalArgumentException(msg);
        }
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
