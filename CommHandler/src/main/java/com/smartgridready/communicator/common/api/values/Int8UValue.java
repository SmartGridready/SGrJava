package com.smartgridready.communicator.common.api.values;

/**
 * Implements an SGr value containing a 8-bit unsigned integer value.
 */
public class Int8UValue extends NumberValue<Short> {

    private Int8UValue(short value) {
        this.value = value;
    }

    @Override
    protected void setValue(double value) {
        checkInt8U((long) value);
        this.value = (short)value;
    }

    @Override
    public Int8UValue[] asArray() {
        return new Int8UValue[]{this};
    }

    @Override
    public void absValue() {
        value = (short)Math.abs(value);
    }

    @Override
    public void roundToInt() {
        // is already an int
    }

    /**
     * Creates a new instance from short.
     * @param value the short value
     * @return an instance of {@link Int8UValue}
     */
    public static Int8UValue of(short value) {
        return new Int8UValue(value);
    }
}
