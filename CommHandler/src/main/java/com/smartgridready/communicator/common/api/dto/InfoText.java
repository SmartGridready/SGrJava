package com.smartgridready.communicator.common.api.dto;

public class InfoText {

    private final String label;
    private final String description;

    public InfoText(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
