package com.smartgridready.communicator.common.api.values;

/**
 * Implements an SGr value containing a 64-bit signed integer value.
 */
public class Int64Value extends NumberValue<Long> {

    private Int64Value(long value) {
        this.value = value;
    }

    @Override
    protected void setValue(double value) {
        this.value = (long)value;
    }

    @Override
    public Int64Value[] asArray() {
        return new Int64Value[]{this};
    }

    @Override
    public void absValue() {
        value = Math.abs(value);
    }

    @Override
    public void roundToInt() {
        // is already int
    }

    /**
     * Creates a new instance from long.
     * @param value the long value
     * @return an instance of {@link Int64Value}
     */
    public static Int64Value of(long value) {
        return new Int64Value(value);
    }
}
