package com.smartgridready.communicator.common.api.values;

/**
 * Implements an SGr value containing a 32-bit signed integer value.
 */
public class Int32Value extends NumberValue<Integer> {

    private Int32Value(int value) {
        this.value = value;
    }

    protected void setValue(double value) {
        checkInt32((long)value);
        this.value = (int)value;
    }

    @Override
    public Int32Value[] asArray() {
        return new Int32Value[]{this};
    }

    @Override
    public void absValue() {
        value = Math.abs(value);
    }

    @Override
    public void roundToInt() {
        // not available
    }

    /**
     * Creates a new instance from int.
     * @param value the int value
     * @return an instance of {@link Int32Value}
     */
    public static Int32Value of(int value) {
        return new Int32Value(value);
    }
}
