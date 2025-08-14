package com.smartgridready.communicator.common.api.values;

/**
 * Implements an SGr value containing a 32-bit floating point value.
 */
public class Float32Value extends NumberValue<Float> {

    private Float32Value(float value) {
        this.value = value;
    }

    @Override
    protected void setValue(double value) {
        checkFloat32(value);
        this.value = (float)value;
    }

    @Override
    public void absValue() {
        value = Math.abs(value);
    }

    @Override
    public void roundToInt() {
        value = (float)Math.round(value);
    }

    /**
     * Creates a new instance.
     * @param value the float value
     * @return an instance of {@link Float32Value}
     */
    public static Float32Value of(float value) {
        return new Float32Value(value);
    }

    @Override
    public Float32Value[] asArray() {
        return new Float32Value[]{this};
    }
}
