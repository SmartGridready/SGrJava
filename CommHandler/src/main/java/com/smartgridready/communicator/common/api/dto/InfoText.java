package com.smartgridready.communicator.common.api.dto;

/**
 * Implements an object label and description.
 */
public class InfoText {

    private final String label;
    private final String description;

    /**
     * Constructs a new instance.
     * @param label the label text
     * @param description the description text
     */
    public InfoText(String label, String description) {
        this.label = label;
        this.description = description;
    }

    /**
     * Gets the label text.
     * @return a string
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets the descriptive text.
     * @return a string
     */
    public String getDescription() {
        return description;
    }
}
