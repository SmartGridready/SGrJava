package com.smartgridready.communicator.contacts.impl;

import java.util.Optional;
import java.util.Properties;

import com.smartgridready.ns.v0.ContactFunctionalProfile;
import com.smartgridready.ns.v0.ContactInterface;
import com.smartgridready.ns.v0.DataPointBase;
import com.smartgridready.ns.v0.DataTypeProduct;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.communicator.common.api.values.BooleanValue;
import com.smartgridready.communicator.common.api.values.EnumRecord;
import com.smartgridready.communicator.common.api.values.EnumValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.common.impl.SGrDeviceBase;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.contacts.GenDriverAPI4Contacts;
import com.smartgridready.driver.api.contacts.GenDriverAPI4ContactsFactory;

public class SGrContactsDevice extends SGrDeviceBase<
        DeviceFrame,
        ContactFunctionalProfile,
        DataPointBase> {

    private GenDriverAPI4Contacts aDriver;

    public SGrContactsDevice(DeviceFrame deviceDescription, GenDriverAPI4ContactsFactory aDriverFactory) {
        super(deviceDescription);

        this.aDriver = aDriverFactory.create(
            getContactInterface().getContactInterfaceDescription().getNumberOfContacts(),
            getContactInterface().getContactInterfaceDescription().getContactStabilisationTimeMs()
        );
    }

    @Override
    public Value getVal(String profileName, String dataPointName) throws GenDriverException {
        DataPointBase dataPoint = findDataPoint(profileName, dataPointName);
        checkReadWritePermission(dataPoint, RwpDirections.READ);

        boolean[] contactStates = aDriver.readContacts(profileName, dataPointName);

        DataTypeProduct dt = dataPoint.getDataPoint().getDataType();
        if (dt.getBoolean() != null) {
            if (contactStates.length == 0) {
                throw new GenDriverException("No contact state read");
            }
            return BooleanValue.of(contactStates[0]);
        } else if (dt.getEnum() != null) {
            int nContacts = getNumberOfContacts(dt.getEnum().getEnumEntry().size());
            if (contactStates.length < nContacts) {
                throw new GenDriverException(String.format("Not enough contact states read (%d, need %s)", contactStates.length, nContacts));
            }
            long ord = getOrdinal(contactStates, nContacts);
            return EnumValue.of(ord, dt.getEnum());
        } else {
            throw new UnsupportedOperationException("only boolean and enum supported");
        }
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

        boolean[] contactStates;
        DataTypeProduct dt = dataPoint.getDataPoint().getDataType();
        if (dt.getBoolean() != null) {
            contactStates = new boolean[] { value.getBoolean() };
        } else if (dt.getEnum() != null) {
            int nContacts = getNumberOfContacts(dt.getEnum().getEnumEntry().size());
            EnumRecord enumR = value.getEnum();
            Long ord = enumR.getOrdinal();
            contactStates = getContactStates(ord, nContacts);
        } else {
            throw new UnsupportedOperationException("only boolean and enum supported");
        }

        aDriver.writeContacts(profileName, dataPointName, contactStates);
    }

    @Override
    public void connect() throws GenDriverException {
        aDriver.connect();
    }

    @Override
    public void disconnect() throws GenDriverException {
        aDriver.disconnect();
    }

    @Override
    public boolean isConnected() {
        return aDriver.isConnected();
    }

    @Override
    protected Optional<ContactFunctionalProfile> findProfile(String profileName) {
        return getContactInterface().getFunctionalProfileList().getFunctionalProfileListElement().stream()
                .filter(functionalProfile -> functionalProfile.getFunctionalProfile().getFunctionalProfileName().equals(profileName))
                .findFirst();
    }

    @Override
    protected Optional<DataPointBase> findDataPointForProfile(ContactFunctionalProfile functionalProfile,
            String dataPointName) {
        return functionalProfile.getDataPointList().getDataPointListElement().stream()
            .filter(dataPoint -> dataPoint.getDataPoint().getDataPointName().equals(dataPointName))
            .findFirst();
    }

    private ContactInterface getContactInterface() {
        return Optional.ofNullable(device.getInterfaceList().getContactInterface()).orElseThrow(() -> new IllegalArgumentException("No contact interface defined in EI-XML"));
    }

    private static int getNumberOfContacts(int numberOfValues) {
        int n = 0;
        int r = numberOfValues;
        if (r % 2 != 0) {
            n++;
            r--;
        }
        while (r > 1) {
            r /= 2;
            n++;
        }
        return n;
    }

    /**
     * Converts multiple boolean contact state to integer.
     * LSB comes first.
     * @param contactStates the contact state
     * @param nContacts the number of contacts to read
     * @return a long integer
     */
    private static long getOrdinal(boolean[] contactStates, int nContacts) {
        long ord = 0;
        for (int i = 0; i < nContacts; i++) {
            ord |= (contactStates[i] ? 1<<i : 0);
        }
        return ord;
    }

    private static boolean[] getContactStates(long ordinal, int nContacts) {
        boolean[] states = new boolean[nContacts];
        long mask = 1;
        for (int i = 0; i < nContacts; i++) {
            states[i] = (ordinal & mask) != 0;
            mask <<= 1;
        }
        return states;
    }
}
