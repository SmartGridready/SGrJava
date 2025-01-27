package com.smartgridready.communicator.common.api.values;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;

public class ArrayValue<T extends Value> extends Value {

    private final T[] values;

    public ArrayValue(T[] values) {
       this.values = values;
    }

    @Override
    public byte getInt8() {
        return values != null && values.length > 0 ? values[0].getInt8() : 0;
    }

    @Override
    public short getInt8U() {
        return values != null && values.length > 0 ? values[0].getInt8U() : 0;
    }

    @Override
    public short getInt16() {
        return values != null && values.length > 0 ? values[0].getInt16() : 0;
    }

    @Override
    public int getInt16U() {
        return values != null && values.length > 0 ? values[0].getInt16U() : 0;
    }

    @Override
    public int getInt32() {
        return values != null && values.length > 0 ? values[0].getInt32() : 0;
    }

    @Override
    public long getInt32U() {
        return values != null && values.length > 0 ? values[0].getInt32U() : 0;
    }

    @Override
    public long getInt64() {
        return values != null && values.length > 0 ? values[0].getInt64() : 0;
    }

    @Override
    public BigInteger getInt64U() {
        return values != null && values.length > 0 ? values[0].getInt64U() : null;
    }

    @Override
    public float getFloat32() {
        return values != null && values.length > 0 ? values[0].getFloat32() : 0;
    }

    @Override
    public double getFloat64() {
        return values != null && values.length > 0 ? values[0].getFloat64() : 0;
    }

    @Override
    public String getString() {
        return values != null && values.length > 0 ? values[0].getString() : null;
    }

    @Override
    public boolean getBoolean() {
        return values != null && values.length > 0 && values[0].getBoolean();
    }

    @Override
    public EnumRecord getEnum() {
        return values != null && values.length > 0 ? values[0].getEnum() : null;
    }

    @Override
    public Map<String, Boolean> getBitmap() {
        return values != null && values.length > 0 ? values[0].getBitmap() : Map.of();
    }

    @Override
    public String getJson() {
        return values != null && values.length > 0 ? values[0].getJson() : null;
    }

    @Override
    public void absValue() {
        Arrays.stream(values).forEach(Value::absValue);
    }

    @Override
    public void roundToInt() {
        Arrays.stream(values).forEach(Value::roundToInt);
    }

    public T[] asArray() {
        return values;
    }

    public static ArrayValue<Value> of(Value ... values) {
        return new ArrayValue<>(values);
    }
}
