package com.smartgridready.communicator.common.api.dto;

import com.smartgridready.ns.v0.DeviceCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a generic device information facade.
 */
public class DeviceInfo {

    private final String name;
    private final String versionNumber;
    private final String manufacturer;
    private final String softwareVersion;
    private final String hardwareVersion;
    private final DeviceCategory deviceCategory;

    private final InterfaceType interfaceType;

    private final OperationEnvironment operationEnvironment;

    private final List<GenericAttribute> genericAttributes;

    private final List<ConfigurationValue> configurationInfo;

    private final List<FunctionalProfile> functionalProfiles;

    /**
     * Constructs a new instance.
     * @param name the device name
     * @param manufacturer the device manufacturer
     * @param versionNumber the EID version number
     * @param softwareVersion the software version supported by the EID
     * @param hardwareVersion the hardware version supported by the EID
     * @param deviceCategory the device category
     * @param interfaceType the type of communication interface
     * @param operationEnvironment the environment the device operates in
     * @param genericAttributes the generic attributes of the device
     * @param configurationInfo the list of EID configuration parameters
     * @param functionalProfiles the list of functional profiles
     */
    public DeviceInfo(String name,
                      String manufacturer,
                      String versionNumber,
                      String softwareVersion,
                      String hardwareVersion,
                      DeviceCategory deviceCategory,
                      InterfaceType interfaceType,
                      OperationEnvironment operationEnvironment,
                      List<GenericAttribute> genericAttributes,
                      List<ConfigurationValue> configurationInfo,
                      List<FunctionalProfile> functionalProfiles) {

        this.name = name;
        this.manufacturer = manufacturer;
        this.versionNumber = versionNumber;
        this.softwareVersion = softwareVersion;
        this.hardwareVersion = hardwareVersion;
        this.deviceCategory = deviceCategory;
        this.interfaceType = interfaceType;
        this.operationEnvironment = operationEnvironment;
        this.genericAttributes = genericAttributes;
        this.configurationInfo = configurationInfo;
        this.functionalProfiles = functionalProfiles;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public DeviceCategory getDeviceCategory() {
        return deviceCategory;
    }

    public InterfaceType getInterfaceType() {
        return interfaceType;
    }

    public OperationEnvironment getOperationEnvironment() {
        return operationEnvironment;
    }

    public List<GenericAttribute> getGenericAttributes() {
        return genericAttributes;
    }

    public List<ConfigurationValue> getConfigurationInfo() {
        return configurationInfo;
    }

    public List<FunctionalProfile> getFunctionalProfiles() {
        return functionalProfiles;
    }

    /**
     * Reads all data point values from device.
     * @return a list of {@link DataPointValue}
     */
    public List<DataPointValue> getValues() {
        List<DataPointValue> dataPointValues = new ArrayList<>();
        functionalProfiles.forEach(functionalProfile -> dataPointValues.addAll(functionalProfile.getValues()));
        return dataPointValues;
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
