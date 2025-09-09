package com.smartgridready.communicator.common.api.dto;

import com.smartgridready.communicator.common.api.values.Value;

/**
 * Implements a data point value with functional profile and data points names.
 */
public class DataPointValue {

    private final String functionalProfileName;
    private final String dataPointName;
    private final Value value;

    /**
     * Constructs a data point value.
     * @param functionalProfileName the functional profile name
     * @param dataPointName the data point name
     * @param value the data point value
     */
    public DataPointValue(String functionalProfileName, String dataPointName, Value value) {
        this.functionalProfileName = functionalProfileName;
        this.dataPointName = dataPointName;
        this.value = value;
    }

    /**
     * Gets the functional profile name.
     * @return the functional profile name
     */
    public String getFunctionalProfileName() {
        return functionalProfileName;
    }

    /**
     * Gets the data point name.
     * @return the data point name.
     */
    public String getDataPointName() {
        return dataPointName;
    }

    /**
     * Gets the data point value.
     * @return an instance of {@link Value}
     */
    public Value getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("profile: %s, dataPoint: %s, value: %s", functionalProfileName, dataPointName, value);
    }

    /**
     * Constructs a data point value.
     * @param functionalProfileName the functional profile name
     * @param dataPointName the data point name
     * @param value the data point value
     * @return the constructed data point value
     */
    public static DataPointValue of(String functionalProfileName, String dataPointName, Value value) {
        return new DataPointValue(functionalProfileName, dataPointName, value);
    }
}
