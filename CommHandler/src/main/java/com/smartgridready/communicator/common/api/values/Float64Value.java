package com.smartgridready.communicator.common.api.values;

/**
 * Implements an SGr value containing a 64-bit floating point value.
 */
public class Float64Value extends NumberValue<Double> {

    private Float64Value(double value) {
        this.value = value;
    }

    @Override
    protected void setValue(double value) {
        this.value = value;
    }

    @Override
    public Float64Value[] asArray() {
        return new Float64Value[]{this};
    }

    @Override
    public void absValue() {
        value = Math.abs(value);
    }

    @Override
    public void roundToInt() {
        value = (double)Math.round(value);
    }

    /**
     * Creates a new instance.
     * @param value the double value
     * @return an instance of {@link Float64Value}
     */
    public static Float64Value of(double value) {
        return new Float64Value(value);
    }
}
