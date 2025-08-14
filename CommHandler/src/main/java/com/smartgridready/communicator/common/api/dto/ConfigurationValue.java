package com.smartgridready.communicator.common.api.dto;

import com.smartgridready.communicator.common.api.values.DataTypeInfo;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.ns.v0.Language;

import java.util.Map;

/**
 * Implements an EID configuration parameters.
 */
public class ConfigurationValue {

    private final String name;
    private final Value defaultValue;
    private final DataTypeInfo dataType;

    private final Map<Language, String> descriptions;

    /**
     * Constructs a new instance.
     * @param name the parameter name
     * @param defaultValue the parameter's default value
     * @param dataType the parameter's data type
     * @param descriptions parameter descriptions
     */
    public ConfigurationValue(String name, Value defaultValue, DataTypeInfo dataType, Map<Language, String> descriptions) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.dataType = dataType;
        this.descriptions = descriptions;
    }

    public String getName() {
        return name;
    }

    public Value getDefaultValue() {
        return defaultValue;
    }

    public DataTypeInfo getDataType() {
        return dataType;
    }

    public Map<Language, String> getDescriptions() {
        return descriptions;
    }
}
