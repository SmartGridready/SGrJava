package com.smartgridready.communicator.common.impl;

import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.FunctionalProfileBase;
import com.smartgridready.communicator.common.api.dto.InterfaceType;

import java.util.List;
import java.util.Collections;

/**
 * Implements a helper class which evaluates the communication interface type and functional profiles
 * according to the specification. 
 */
class DeviceWithInterface {

    private final InterfaceType interfaceType;

    private final List<? extends FunctionalProfileBase> functionalProfiles;

    private DeviceWithInterface(DeviceFrame device) {

        if (device.getInterfaceList().getModbusInterface() != null) {
            interfaceType = InterfaceType.MODBUS;
            functionalProfiles = device.getInterfaceList().getModbusInterface().getFunctionalProfileList().getFunctionalProfileListElement();
        }
        else if (device.getInterfaceList().getRestApiInterface() != null) {
            interfaceType = InterfaceType.RESTAPI;
            functionalProfiles = device.getInterfaceList().getRestApiInterface().getFunctionalProfileList().getFunctionalProfileListElement();
        }
        else if (device.getInterfaceList().getMessagingInterface() != null) {
            interfaceType = InterfaceType.MESSAGING;
            functionalProfiles = device.getInterfaceList().getMessagingInterface().getFunctionalProfileList().getFunctionalProfileListElement();
        }
        else if (device.getInterfaceList().getContactInterface() != null) {
            interfaceType = InterfaceType.CONTACT;
            functionalProfiles = device.getInterfaceList().getContactInterface().getFunctionalProfileList().getFunctionalProfileListElement();
        }
        else if (device.getInterfaceList().getGenericInterface() != null) {
            interfaceType = InterfaceType.GENERIC;
            functionalProfiles = device.getInterfaceList().getGenericInterface().getFunctionalProfileList().getFunctionalProfileListElement();
        }
        else {
            interfaceType = InterfaceType.UNKNOWN;
            functionalProfiles = Collections.emptyList();
        }
    }

    /**
     * Gets the functional profiles of the device.
     * @return a list of {@link FunctionalProfileBase}, actual instances may derive from
     */
    @SuppressWarnings("unchecked")
    public List<FunctionalProfileBase> getFunctionalProfiles() {
        return (List<FunctionalProfileBase>)functionalProfiles;
    }

    /**
     * Gets the communication interface type.
     * @return an instance of {@link InterfaceType}
     */
    public InterfaceType getInterfaceType() {
        return interfaceType;
    }

    /**
     * Creates a new instance.
     * @param device the device specification
     * @return an instance of {@link DeviceWithInterface}
     */
    public static DeviceWithInterface of(DeviceFrame device) {
        return new DeviceWithInterface(device);
    }
}
