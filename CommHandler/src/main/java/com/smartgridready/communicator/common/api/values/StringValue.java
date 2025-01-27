package com.smartgridready.communicator.common.api.values;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigInteger;
import java.util.Map;

public class StringValue extends Value {

    private String value;

    private StringValue(String value) {
        this.value = value;
    }

    @Override
    public byte getInt8() {
        long val = toLong();
        checkInt8(val);
        return (byte)val;
    }

    @Override
    public short getInt8U() {
        long val = toLong();
        checkInt8U(val);
        return (short) val;
    }

    @Override
    public short getInt16() {
        long val = toLong();
        checkInt16(val);
        return (short) val;
    }

    @Override
    public int getInt16U() {
        long val = toLong();
        checkInt16U(val);
        return (int) val;
    }

    @Override
    public int getInt32() {
        long val = toLong();
        checkInt32(val);
        return (int) val;
    }

    @Override
    public long getInt32U() {
        long val = toLong();
        checkInt32U(val);
        return val;
    }

    @Override
    public long getInt64() {
        BigInteger val = new BigInteger(value);
        checkInt64(val);
        return val.longValue();
    }

    @Override
    public BigInteger getInt64U() {
        BigInteger val = new BigInteger(value);
        checkInt64U(val);
        return val;
    }

    @Override
    public float getFloat32() {
        double val = toDouble();
        checkFloat32(val);
        return (float) val;
    }

    @Override
    public double getFloat64() {
        double val = toDouble();
        checkFloat32(val);
        return val;
    }

    @Override
    public String getString() {
        return value;
    }

    @Override
    public boolean getBoolean() {
        try {
            long intValue = getInt64();
            return intValue != 0;
        } catch (NumberFormatException e) {
            return Boolean.parseBoolean(value);
        }
    }

    @Override
    public EnumRecord getEnum() {
        throw new UnsupportedOperationException("Cannot convert from String value to enum");
    }

    @Override
    public Map<String, Boolean> getBitmap() {
        throw new UnsupportedOperationException("Cannot convert from String to a bitmap.");
    }

    @Override
    public String getJson() {
        // pure JSON string should be enclosed in double-quotes
        return "\"" + value + "\"";
    }

    public void scaleDown(int mul, int powOf10) {
        if (mul != 1 || powOf10 !=0) {
            double dVal = toDouble() / mul;
            value = String.valueOf(dVal * Math.pow(10.0, -powOf10));
        }
    }

    public void scaleUp(int mul, int powOf10) {
        if (mul != 1 || powOf10 !=0) {
            double dVal = toDouble() * mul;
            value = String.valueOf(dVal * Math.pow(10.0, powOf10));
        }
    }

    @Override
    public StringValue[] asArray() {
        return new StringValue[]{this};
    }

    @Override
    public void absValue() {
        double dVal = toDouble();
        value = String.valueOf(Math.abs(dVal));
    }

    @Override
    public void roundToInt() {
        double dVal = toDouble();
        value = String.valueOf(Math.round(dVal));
    }

    @Override
    public String toString() {
        return value;
    }

    public static StringValue of(String value) {
        return new StringValue(value);
    }

    private long toLong() {
        return Long.parseLong(value);
    }

    private double toDouble() {
        return Double.parseDouble(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;

        if (o==null || getClass()!=o.getClass()) return false;

        StringValue that = (StringValue) o;

        return new EqualsBuilder().append(value, that.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }
}
