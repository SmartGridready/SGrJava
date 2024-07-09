package communicator.generic.impl;

import java.util.Optional;

import com.smartgridready.ns.v0.DataPointBase;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.GenericFunctionalProfile;
import com.smartgridready.ns.v0.GenericInterface;

import communicator.common.api.values.Value;
import communicator.common.impl.SGrDeviceBase;
import communicator.common.runtime.GenDriverException;
import communicator.common.runtime.GenDriverSocketException;

public class SGrGenericDevice extends SGrDeviceBase<
        DeviceFrame,
        GenericFunctionalProfile,
        DataPointBase> {

    public SGrGenericDevice(DeviceFrame deviceDescription) {
        super(deviceDescription);
    }

    @Override
    public Value getVal(String profileName, String dataPointName) throws GenDriverException {
        DataPointBase dataPoint = findDatapoint(profileName, dataPointName);
        checkReadWritePermission(dataPoint, RwpDirections.READ);

        // TODO use local cache
        throw new UnsupportedOperationException("generic device getVal not implemented yet");
    }

    @Override
    public void setVal(String profileName, String dataPointName, Value value)
            throws GenDriverException, GenDriverSocketException {
        DataPointBase dataPoint = findDatapoint(profileName, dataPointName);
        checkReadWritePermission(dataPoint, RwpDirections.WRITE);

        // TODO use local cache
        throw new UnsupportedOperationException("generic device setVal not implemented yet");
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
    protected Optional<GenericFunctionalProfile> findProfile(String profileName) {
        return getGenericInterface().getFunctionalProfileList().getFunctionalProfileListElement().stream()
                .filter(functionalProfile -> functionalProfile.getFunctionalProfile().getFunctionalProfileName().equals(profileName))
                .findFirst();
    }

    @Override
    protected Optional<DataPointBase> findDataPointForProfile(GenericFunctionalProfile functionalProfile,
            String datapointName) {
        return functionalProfile.getDataPointList().getDataPointListElement().stream()
            .filter(dataPoint -> dataPoint.getDataPoint().getDataPointName().equals(datapointName))
            .findFirst();
    }

    private GenericInterface getGenericInterface() {
        return Optional.ofNullable(device.getInterfaceList().getGenericInterface()).orElseThrow(() -> new IllegalArgumentException("No generic interface defined in EI-XML"));
    }
}
