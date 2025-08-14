package com.smartgridready.communicator.common.api.values;

/**
 * Implements an SGr value containing a 8-bit signed integer value.
 */
public class Int8Value extends NumberValue<Byte> {

    private Int8Value(byte value) {
        this.value = value;
    }

    @Override
    protected void setValue(double value) {
        checkInt8((long) value);
        this.value = (byte)value;
    }

    @Override
    public Int8Value[] asArray() {
        return new Int8Value[]{this};
    }

    @Override
    public void absValue() {
        value = (byte)Math.abs(value);
    }

    @Override
    public void roundToInt() {
        // is already int
    }

    /**
     * Creates a new instance from byte.
     * @param value the byte value
     * @return an instance of {@link Int8Value}
     */
    public static Int8Value of(byte value) {
        return new Int8Value(value);
    }
}
