package com.smartgridready.communicator.common.api.dto;

import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.ns.v0.FunctionalProfileCategory;
import io.vavr.control.Try;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a generic functional profile facade.
 */
public class FunctionalProfile {

    private final String name;
    private final String profileType;
    private final FunctionalProfileCategory category;

    private final List<GenericAttribute> genericAttributes;
    private final List<DataPoint> dataPoints;

    /**
     * Constructs a new instance.
     * @param name the functional profile name
     * @param profileType the functional profile type
     * @param category the functional profile category
     * @param genericAttributes the generic attributes
     * @param dataPoints the functional profile's data points
     */
    public FunctionalProfile(String name,
                             String profileType,
                             FunctionalProfileCategory category,
                             List<GenericAttribute> genericAttributes,
                             List<DataPoint> dataPoints) {
        this.name = name;
        this.profileType = profileType;
        this.category = category;
        this.genericAttributes = genericAttributes;
        this.dataPoints = dataPoints;
    }

    /**
     * Gets the functional profile name.
     * @return a string
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the functional profile type.
     * @return a string
     */
    public String getProfileType() {
        return profileType;
    }

    /**
     * Gets the functional profile category.
     * @return an instance of {@link FunctionalProfileCategory}
     */
    public FunctionalProfileCategory getCategory() {
        return category;
    }

    /**
     * Gets the generic attributes.
     * @return a list of {@link GenericAttribute}
     */
    public List<GenericAttribute> getGenericAttributes() {
        return genericAttributes;
    }

    /**
     * Gets the data points.
     * @return a list of {@link DataPoint}
     */
    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    /**
     * Reads all data point values from device.
     * @return a list of {@link DataPointValue}
     */
    public List<DataPointValue> getValues() {

        final List<DataPointValue> valueRecords = new ArrayList<>();

        dataPoints.forEach(dataPoint -> {
            // include readable data points
            final String perm = dataPoint.getPermissions().value();
            if (perm.contains("R") || perm.contains("C")) {
                valueRecords.add(DataPointValue.of(
                    name,
                    dataPoint.getName(),
                    Try.of(dataPoint::getVal).getOrElseGet(e -> StringValue.of(e.getMessage()))
                ));
            }
        });

        return valueRecords;
    }

    /**
     * Reads all data point values from device.
     * @return a list of {@link DataPointValue}
     * @deprecated Since version 2.1.0
     */
    @Deprecated(since = "2.1.0", forRemoval = true)
    public List<DataPointValue> readData() {
        return getValues();
    }
}
