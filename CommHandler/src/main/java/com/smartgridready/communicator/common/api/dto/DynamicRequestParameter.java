package com.smartgridready.communicator.common.api.dto;

import com.smartgridready.communicator.common.api.values.DataTypeInfo;
import com.smartgridready.ns.v0.Language;

import java.util.Map;

public class DynamicRequestParameter {

    private final String name;
    private final String defaultValue;
    private final DataTypeInfo dataType;
    private final Map<Language, InfoText> descriptions;


    public DynamicRequestParameter(String name, String defaultValue, DataTypeInfo dataType, Map<Language, InfoText> descriptions) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.dataType = dataType;
        this.descriptions = descriptions;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public DataTypeInfo getDataType() {
        return dataType;
    }

    public Map<Language, InfoText> getDescriptions() {
        return descriptions;
    }
}
