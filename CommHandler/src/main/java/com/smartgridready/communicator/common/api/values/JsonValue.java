package com.smartgridready.communicator.common.api.values;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.LongNode;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonValue extends Value {

    private JsonNode value;

    private JsonValue(JsonNode value) {
        this.value = value;
    }

    @Override
    public byte getInt8() {
        long val;
        if (value.isNumber()) {
            val = value.longValue();
        } else if (value.isBoolean() || value.isTextual()) {
            val = value.asLong();
        } else {
            throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int8, node type is '%s'.", value.getNodeType()));
        }
        checkInt8(val);
        return (byte) val;
    }

    @Override
    public short getInt8U() {
        long val;
        if (value.isNumber()) {
            val = value.longValue();
        } else if (value.isBoolean() || value.isTextual()) {
            val = value.asLong();
        } else {
            throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int8U, node type is '%s'.", value.getNodeType()));
        }
        checkInt8U(val);
        return (short) val;
    }

    @Override
    public short getInt16() {
        long val;
        if (value.isNumber()) {
            val = value.longValue();
        } else if (value.isBoolean() || value.isTextual()) {
            val = value.asLong();
        } else {
            throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int16, node type is '%s'.", value.getNodeType()));
        }
        checkInt16(val);
        return (short) val;
    }

    @Override
    public int getInt16U() {
        long val;
        if (value.isNumber()) {
            val = value.longValue();
        } else if (value.isBoolean() || value.isTextual()) {
            val = value.asLong();
        } else {
            throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int16U, node type is '%s'.", value.getNodeType()));
        }
        checkInt16U(val);
        return (int) val;
    }

    @Override
    public int getInt32() {
        long val;
        if (value.isNumber()) {
            val = value.longValue();
        } else if (value.isBoolean() || value.isTextual()) {
            val = value.asLong();
        } else {
            throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int32, node type is '%s'.", value.getNodeType()));
        }
        checkInt32(val);
        return (int) val;
    }

    @Override
    public long getInt32U() {
        long val;
        if (value.isNumber()) {
            val = value.longValue();
        } else if (value.isBoolean() || value.isTextual()) {
            val = value.asLong();
        } else {
            throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int32U, node type is '%s'.", value.getNodeType()));
        }
        checkInt32U(val);
        return val;
    }

    @Override
    public long getInt64() {
        BigInteger val;
        if (value.isNumber()) {
            val = value.bigIntegerValue();
        } else if (value.isBoolean() || value.isTextual()) {
            val = BigInteger.valueOf(value.asLong());
        } else {
            throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int64, node type is '%s'.", value.getNodeType()));
        }
        checkInt64(val);
        return val.longValue();
    }

    @Override
    public BigInteger getInt64U() {
        BigInteger val;
        if (value.isNumber()) {
            val = value.bigIntegerValue();
        } else if (value.isBoolean()) {
            val = BigInteger.valueOf(value.asLong());
        } else if (value.isTextual()) {
            val = new BigInteger(value.textValue());
        } else {
            throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int64U, node type is '%s'.", value.getNodeType()));
        }
        checkInt64U(val);
        return val;
    }

    @Override
    public float getFloat32() {
        double val;
        if (value.isNumber()) {
            val = value.doubleValue();
        } else if (value.isBoolean() || value.isTextual()) {
            val = value.asDouble();
        } else {
            throw new UnsupportedOperationException(String.format("Cannot convert from JSON to float32, node type is '%s'.", value.getNodeType()));
        }
        checkFloat32(val);
        return (float)val;
    }

    @Override
    public double getFloat64() {
        double val;
        if (value.isNumber()) {
            val = value.doubleValue();
        } else if (value.isBoolean() || value.isTextual()) {
            val = value.asDouble();
        } else {
            throw new UnsupportedOperationException(String.format("Cannot convert from JSON to float64, node type is '%s'.", value.getNodeType()));
        }
        return val;
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
        boolean val;
        if (value.isBoolean()) {
            val = value.booleanValue();
        } else if (value.isNumber() || value.isTextual()) {
            val = value.asBoolean();
        } else {
            throw new UnsupportedOperationException(String.format("Cannot convert from JSON to boolean, node type is '%s'.", value.getNodeType()));
        }
        return val;
    }

    @Override
    public EnumRecord getEnum() {
        if (value.isValueNode()) {
            return new EnumRecord(value.asText(), null, null);
        }
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON object to enum, node type is '%s'.", value.getNodeType()));
    }

    @Override
    public Map<String, Boolean> getBitmap() {
        if (value.isObject()) {
            Map<String, Boolean> bm = new HashMap<>();
            // iterate through children and convert to boolean
            value.fields().forEachRemaining(e -> {
                if (e.getValue().isValueNode()) {
                    bm.put(e.getKey(), e.getValue().asBoolean());
                }
            });
            return bm;
        }
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON to bitmap, node type is '%s'.", value.getNodeType()));
    }

    @Override
    public Instant getDateTime() {
        if (value.isTextual()) {
            return Instant.parse(value.textValue());
        } else if (value.isNumber()) {
            return Instant.ofEpochMilli(value.longValue());
        }
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON object to datetime, node type is '%s'.", value.getNodeType()));
    }

    @Override
    public JsonNode getJson() {
        return value;
    }

    @Override
    public JsonValue[] asArray() {
        if (value.isArray()) {
            // make array of JsonValue from each element
            final List<JsonValue> arr = new ArrayList<>();
            value.elements().forEachRemaining(e -> arr.add(JsonValue.of(e)));
            return arr.toArray(new JsonValue[0]);
        }
        return new JsonValue[]{this};
    }

    @Override
    public void absValue() {
        if (value.isFloatingPointNumber()) {
            value = DoubleNode.valueOf(Math.abs(value.doubleValue()));
        } else if (value.isIntegralNumber()) {
            value = LongNode.valueOf(Math.abs(value.longValue()));
        }
    }

    @Override
    public void roundToInt() {
        if (value.isFloatingPointNumber()) {
            value = LongNode.valueOf(Math.round(value.doubleValue()));
        }
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
