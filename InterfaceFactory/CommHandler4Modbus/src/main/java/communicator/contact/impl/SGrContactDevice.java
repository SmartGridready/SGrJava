package communicator.contact.impl;

import java.util.Optional;

import com.smartgridready.ns.v0.ContactFunctionalProfile;
import com.smartgridready.ns.v0.ContactInterface;
import com.smartgridready.ns.v0.DataPointBase;
import com.smartgridready.ns.v0.DeviceFrame;

import communicator.common.api.values.Value;
import communicator.common.impl.SGrDeviceBase;
import communicator.common.runtime.GenDriverException;

public class SGrContactDevice extends SGrDeviceBase<
        DeviceFrame,
        ContactFunctionalProfile,
        DataPointBase> {

    public SGrContactDevice(DeviceFrame deviceDescription) {
        super(deviceDescription);
        // TODO use driver interface factory with communicator-specific implementation
    }

    @Override
    public Value getVal(String profileName, String dataPointName) throws GenDriverException {
        DataPointBase dataPoint = findDatapoint(profileName, dataPointName);
        checkReadWritePermission(dataPoint, RwpDirections.READ);

        // TODO use driver interface
        throw new UnsupportedOperationException("contact device getVal not implemented yet");
    }

    @Override
    public void setVal(String profileName, String dataPointName, Value value)
            throws GenDriverException {
        DataPointBase dataPoint = findDatapoint(profileName, dataPointName);
        checkReadWritePermission(dataPoint, RwpDirections.WRITE);

        // TODO use driver interface
        throw new UnsupportedOperationException("contact device setVal not implemented yet");
    }

    @Override
    public void connect() throws GenDriverException {
        // TODO use driver interface
    }

    @Override
    public void disconnect() throws GenDriverException {
        // TODO use driver interface
    }

    @Override
    protected Optional<ContactFunctionalProfile> findProfile(String profileName) {
        return getContactInterface().getFunctionalProfileList().getFunctionalProfileListElement().stream()
                .filter(functionalProfile -> functionalProfile.getFunctionalProfile().getFunctionalProfileName().equals(profileName))
                .findFirst();
    }

    @Override
    protected Optional<DataPointBase> findDataPointForProfile(ContactFunctionalProfile functionalProfile,
            String datapointName) {
        return functionalProfile.getDataPointList().getDataPointListElement().stream()
            .filter(dataPoint -> dataPoint.getDataPoint().getDataPointName().equals(datapointName))
            .findFirst();
    }

    private ContactInterface getContactInterface() {
        return Optional.ofNullable(device.getInterfaceList().getContactInterface()).orElseThrow(() -> new IllegalArgumentException("No contact interface defined in EI-XML"));
    }
}
