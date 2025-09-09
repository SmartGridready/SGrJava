package com.smartgridready.communicator.common.api.dto;

import com.smartgridready.communicator.common.api.values.DataTypeInfo;
import com.smartgridready.ns.v0.Language;

import java.util.Map;

/**
 * Implements a dynamic request parameter.
 */
public class DynamicRequestParameter {

    private final String name;
    private final String defaultValue;
    private final DataTypeInfo dataType;
    private final Map<Language, InfoText> descriptions;

    /**
     * Constructs a new instance.
     * @param name the parameter name
     * @param defaultValue the parameter's default value
     * @param dataType the parameter's data type
     * @param descriptions parameter descriptions
     */
    public DynamicRequestParameter(String name, String defaultValue, DataTypeInfo dataType, Map<Language, InfoText> descriptions) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.dataType = dataType;
        this.descriptions = descriptions;
    }

    /**
     * Gets the parameter name.
     * @return a string
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the parameter's default value.
     * @return a string
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Gets the parameter's data type.
     * @return a data type info
     */
    public DataTypeInfo getDataType() {
        return dataType;
    }

    /**
     * Gets the text descriptions.
     * @return a map of language to description
     */
    public Map<Language, InfoText> getDescriptions() {
        return descriptions;
    }
}
