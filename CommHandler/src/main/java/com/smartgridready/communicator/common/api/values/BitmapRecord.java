package com.smartgridready.communicator.common.api.values;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Implements a custom bit map record.
 */
public class BitmapRecord {

    private String literal;
    private boolean value;
    private String description;

    /**
     * Constructs a new instance.
     * @param literal the map key name
     * @param value the bit value
     * @param description the record description
     */
    public BitmapRecord(String literal, boolean value, String description) {
        this.literal = literal;
        this.value = value;
        this.description = description;
    }

    /**
     * Gets the map key name.
     * @return a string
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Gets the bit value.
     * @return a boolean
     */
    public boolean getValue() {
        return value;
    }

    /**
     * Gets the record description.
     * @return a string
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return (literal!=null ? literal:"undef")
                + ":" + value
                + " | " + (description!=null ? description:"");
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;

        if (o==null || getClass()!=o.getClass()) return false;

        BitmapRecord that = (BitmapRecord) o;

        return new EqualsBuilder().append(value, that.value).append(literal, that.literal).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(literal).append(value).toHashCode();
    }
}
