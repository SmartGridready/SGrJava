package com.smartgridready.communicator.common.impl;

import java.util.List;

import com.smartgridready.ns.v0.ContactFunctionalProfile;
import com.smartgridready.ns.v0.DataPointBase;
import com.smartgridready.ns.v0.FunctionalProfileBase;
import com.smartgridready.ns.v0.GenericDataPointList;
import com.smartgridready.ns.v0.GenericFunctionalProfile;
import com.smartgridready.ns.v0.MessagingFunctionalProfile;
import com.smartgridready.ns.v0.ModbusFunctionalProfile;
import com.smartgridready.ns.v0.RestApiFunctionalProfile;

class FunctionalProfileWithDataPoints {

    private final List<? extends DataPointBase> dataPoints;

    private FunctionalProfileWithDataPoints(FunctionalProfileBase functionalProfile) {

        if (functionalProfile instanceof ModbusFunctionalProfile) {
            dataPoints = ((ModbusFunctionalProfile)functionalProfile).getDataPointList().getDataPointListElement();
        }
        else if (functionalProfile instanceof RestApiFunctionalProfile) {
            dataPoints = ((RestApiFunctionalProfile)functionalProfile).getDataPointList().getDataPointListElement();
        }
        else if (functionalProfile instanceof MessagingFunctionalProfile) {
            dataPoints = ((MessagingFunctionalProfile)functionalProfile).getDataPointList().getDataPointListElement();
        }
        else if (functionalProfile instanceof ContactFunctionalProfile) {
            dataPoints = ((ContactFunctionalProfile)functionalProfile).getDataPointList().getDataPointListElement();
        }
        else if (functionalProfile instanceof GenericFunctionalProfile) {
            dataPoints = ((GenericFunctionalProfile)functionalProfile).getDataPointList().getDataPointListElement();
        }
        else {
            dataPoints = new GenericDataPointList().getDataPointListElement();
        }
    }

    public static FunctionalProfileWithDataPoints of (FunctionalProfileBase functionalProfile) {
        return new FunctionalProfileWithDataPoints(functionalProfile);
    }

    @SuppressWarnings("unchecked")
    public List<DataPointBase> getDataPoints() {
        return (List<DataPointBase>) dataPoints;
    }
}
