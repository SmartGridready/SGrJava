package com.smartgridready.communicator.common.api.values;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Implements a custom enumeration record.
 */
public class EnumRecord {

    /** Placeholder for undefined literals. */
    public static final String UNDEFINED_LITERAL = "UNDEFINED";
    /** Placeholder for undefined ordinal. */
    public static final long UNDEFINED_ORDINAL = 0;

    private final String literal;
    private final Long ordinal;
    private final String description;

    /**
     * Constructs a new instance.
     * @param literal the text literal
     * @param ordinal the numeric ordinal
     * @param description the record description
     */
    public EnumRecord(String literal, Long ordinal, String description) {
        this.literal = literal;
        this.ordinal = ordinal;
        this.description = description;
    }

    /**
     * Gets the text literal.
     * @return a string
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Gets the numeric ordinal.
     * @return an integer
     */
    public Long getOrdinal() {
        return ordinal;
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
        return ((literal != null) ? literal : UNDEFINED_LITERAL)
                + ":" + ((ordinal != null) ? String.valueOf(ordinal) : String.valueOf(UNDEFINED_ORDINAL))
                + " | " + ((description != null) ? description : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EnumRecord that = (EnumRecord) o;

        return new EqualsBuilder()
            .append(literal, that.literal)
            .append(ordinal, that.ordinal)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(literal).append(ordinal).toHashCode();
    }
}
