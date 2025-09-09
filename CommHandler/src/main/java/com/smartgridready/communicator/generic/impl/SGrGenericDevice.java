package com.smartgridready.communicator.generic.impl;

import java.util.Optional;
import java.util.Properties;

import com.smartgridready.ns.v0.DataPointBase;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.GenericFunctionalProfile;
import com.smartgridready.ns.v0.GenericInterface;
import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.common.impl.SGrDeviceBase;
import com.smartgridready.driver.api.common.GenDriverException;

/**
 * Implements a generic device, which has no actual communication interface.
 * Supports constant data points only.
 */
public class SGrGenericDevice extends SGrDeviceBase<
        DeviceFrame,
        GenericFunctionalProfile,
        DataPointBase> {


    /**
     * Constructs a new instance.
     * @param deviceDescription the EID description
     */
    public SGrGenericDevice(DeviceFrame deviceDescription) {
        super(deviceDescription);
    }

    @Override
    public Value getVal(String profileName, String dataPointName) throws GenDriverException {
        DataPointBase dataPoint = findDataPoint(profileName, dataPointName);
        // only constants supported in concrete case
        checkReadWritePermission(dataPoint, RwpDirections.READ);
        return StringValue.of(dataPoint.getDataPoint().getValue());
    }

    @Override
    public Value getVal(String profileName, String dataPointName, Properties parameters)
            throws GenDriverException {
        return getVal(profileName, dataPointName);
    }

    @Override
    public void setVal(String profileName, String dataPointName, Value value)
            throws GenDriverException {
        DataPointBase dataPoint = findDataPoint(profileName, dataPointName);
        checkReadWritePermission(dataPoint, RwpDirections.WRITE);
        throw new UnsupportedOperationException("generic device setVal not supported");
    }

    @Override
    public void connect() throws GenDriverException {
        // nothing
    }

    @Override
    public void disconnect() throws GenDriverException {
        // nothing
    }

    @Override
    public boolean isConnected() {
        // nope
        return false;
    }

    @Override
    protected Optional<GenericFunctionalProfile> findProfile(String profileName) {
        return getGenericInterface().getFunctionalProfileList().getFunctionalProfileListElement().stream()
                .filter(functionalProfile -> functionalProfile.getFunctionalProfile().getFunctionalProfileName().equals(profileName))
                .findFirst();
    }

    @Override
    protected Optional<DataPointBase> findDataPointForProfile(GenericFunctionalProfile functionalProfile,
            String dataPointName) {
        return functionalProfile.getDataPointList().getDataPointListElement().stream()
            .filter(dataPoint -> dataPoint.getDataPoint().getDataPointName().equals(dataPointName))
            .findFirst();
    }

    private GenericInterface getGenericInterface() {
        return Optional.ofNullable(device.getInterfaceList().getGenericInterface()).orElseThrow(() -> new IllegalArgumentException("No generic interface defined in EI-XML"));
    }
}
