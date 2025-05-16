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
        if (value.isNumber()) {
            long val = value.longValue();
            checkInt8(val);
            return (byte) val;
        }
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int8, node type is '%s'.", value.getNodeType()));
    }

    @Override
    public short getInt8U() {
        if (value.isNumber()) {
            long val = value.longValue();
            checkInt8U(val);
            return (short) val;
        }
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int8U, node type is '%s'.", value.getNodeType()));
    }

    @Override
    public short getInt16() {
        if (value.isNumber()) {
            long val = value.longValue();
            checkInt16(val);
            return (short) val;
        }
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int16, node type is '%s'.", value.getNodeType()));
    }

    @Override
    public int getInt16U() {
        if (value.isNumber()) {
            long val = value.longValue();
            checkInt16U(val);
            return (int) val;
        }
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int16U, node type is '%s'.", value.getNodeType()));
    }

    @Override
    public int getInt32() {
        if (value.isNumber()) {
            long val = value.longValue();
            checkInt32(val);
            return (int) val;
        }
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int32, node type is '%s'.", value.getNodeType()));
    }

    @Override
    public long getInt32U() {
        if (value.isNumber()) {
            long val = value.longValue();
            checkInt32U(val);
            return val;
        }
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int32U, node type is '%s'.", value.getNodeType()));
    }

    @Override
    public long getInt64() {
        if (value.isNumber()) {
            BigInteger val = value.bigIntegerValue();
            checkInt64(val);
            return val.longValue();
        }
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int64, node type is '%s'.", value.getNodeType()));
    }

    @Override
    public BigInteger getInt64U() {
        if (value.isNumber()) {
            BigInteger val = value.bigIntegerValue();
            checkInt64U(val);
            return val;
        } 
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON to int64U, node type is '%s'.", value.getNodeType()));
    }

    @Override
    public float getFloat32() {
        if (value.isNumber()) {
            float val = value.floatValue();
            checkFloat32(val);
            return val;
        }
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON to float32, node type is '%s'.", value.getNodeType()));
    }

    @Override
    public double getFloat64() {
        if (value.isNumber()) {
            double val = value.doubleValue();
            checkFloat32(val);
            return val;
        }
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON to float64, node type is '%s'.", value.getNodeType()));
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
        } else if (value.isTextual()) {
            return value.asBoolean();
        }
        throw new UnsupportedOperationException(String.format("Cannot convert from JSON to boolean, node type is '%s'.", value.getNodeType()));
    }

    @Override
    public EnumRecord getEnum() {
        if (value.isTextual()) {
            return new EnumRecord(value.textValue(), null, null);
        } else if (value.isIntegralNumber()) {
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
