package com.smartgridready.communicator.common.api.values;

/**
 * Implements an SGr value containing a 16-bit signed integer value.
 */
public class Int16Value extends NumberValue<Short> {

    private Int16Value(short value) {
        this.value = value;
    }

    @Override
    protected void setValue(double value) {
        checkInt16((long) value);
        this.value = (short)value;
    }

    @Override
    public Int16Value[] asArray() {
        return new Int16Value[]{this};
    }

    @Override
    public void absValue() {
        value = (short)Math.abs(value);
    }

    @Override
    public void roundToInt() {
        // not available
    }

    /**
     * Creates a new instance from short.
     * @param value the short value
     * @return an instance of {@link Int16Value}
     */
    public static Int16Value of(short value) {
        return new Int16Value(value);
    }
}
