package communicator.common.api;

import java.math.BigInteger;

public class BooleanValue extends Value {

    private boolean value;

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
    public boolean getBoolean() {
        return value;
    }

    @Override
    public void scaleDown(int mul, int powOf10) {
        // no scaling for boolean
    }

    @Override
    public void scaleUp(int mul, int powOf10) {
        // no scaling for boolean
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
}
