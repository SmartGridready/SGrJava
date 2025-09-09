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

/**
 * Implements a helper class which evaluates the functional profiles and data points
 * according to the interface-specific specification. 
 */
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

    /**
     * Creates a new instance.
     * @param functionalProfile the functional profile
     * @return a new instance of {@link FunctionalProfileWithDataPoints}
     */
    public static FunctionalProfileWithDataPoints of(FunctionalProfileBase functionalProfile) {
        return new FunctionalProfileWithDataPoints(functionalProfile);
    }

    /**
     * Gets the list of data points.
     * @return a list of subclasses of {@link DataPointBase}
     */
    @SuppressWarnings("unchecked")
    public List<DataPointBase> getDataPoints() {
        return (List<DataPointBase>) dataPoints;
    }
}
