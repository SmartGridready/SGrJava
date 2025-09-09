package com.smartgridready.communicator.common.api.values;

/**
 * Implements an SGr value containing a 16-bit unsigned integer value.
 */
public class Int16UValue extends NumberValue<Integer> {

    private Int16UValue(int value) {
        this.value = value;
    }

    @Override
    protected void setValue(double value) {
        checkInt16U((long) value);
        this.value = (int)value;
    }

    @Override
    public Int16UValue[] asArray() {
        return new Int16UValue[]{this};
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
     * Creates a new instance from int.
     * @param value the int value
     * @return an instance of {@link Int16UValue}
     */
    public static Int16UValue of(int value) {
        return new Int16UValue(value);
    }
}
