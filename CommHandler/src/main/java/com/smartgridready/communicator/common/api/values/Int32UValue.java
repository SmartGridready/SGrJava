package com.smartgridready.communicator.common.api.values;

/**
 * Implements an SGr value containing a 32-bit unsigned integer value.
 */
public class Int32UValue extends NumberValue<Long> {

    private Int32UValue(long value) {
        this.value = value;
    }

    @Override
    protected void setValue(double value) {
        checkInt32U((long)value);
        this.value = (long)value;
    }

    @Override
    public Int32UValue[] asArray() {
        return new Int32UValue[]{this};
    }

    @Override
    public void absValue() {
        value = Math.abs(value);
    }

    @Override
    public void roundToInt() {
        // is already an int
    }

    /**
     * Creates a new instance from long.
     * @param value the long value
     * @return an instance of {@link Int32UValue}
     */
    public static Int32UValue of(long value) {
        return new Int32UValue(value);
    }
}
