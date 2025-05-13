package com.smartgridready.communicator.common.api.dto;

import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.ns.v0.DataDirectionProduct;
import com.smartgridready.ns.v0.FunctionalProfileCategory;
import io.vavr.control.Try;

import java.util.ArrayList;
import java.util.List;

public class FunctionalProfile {

    private final String name;
    private final String profileType;
    private final FunctionalProfileCategory category;

    private final List<GenericAttribute> genericAttributes;
    private final List<DataPoint> dataPoints;

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

    public String getName() {
        return name;
    }

    public String getProfileType() {
        return profileType;
    }

    public FunctionalProfileCategory getCategory() {
        return category;
    }

    public List<GenericAttribute> getGenericAttributes() {
        return genericAttributes;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

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

    @Deprecated
    public List<DataPointValue> readData() {
        return getValues();
    }
}
