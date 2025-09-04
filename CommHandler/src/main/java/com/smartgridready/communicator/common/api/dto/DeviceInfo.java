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

    /**
     * Gets the device name.
     * @return a string
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the device manufacturer name.
     * @return a string
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Gets the EID version number.
     * @return a string
     */
    public String getVersionNumber() {
        return versionNumber;
    }

    /**
     * Gets the device software version compatible with the EID.
     * @return a string
     */
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    /**
     * Gets the device hardware version compatible with the EID.
     * @return a string
     */
    public String getHardwareVersion() {
        return hardwareVersion;
    }

    /**
     * Gets the device category, defined by XML schema.
     * @return an instance of {@link DeviceCategory}
     */
    public DeviceCategory getDeviceCategory() {
        return deviceCategory;
    }

    /**
     * Gets the type of communication interface.
     * @return an instance of {@link InterfaceType}
     */
    public InterfaceType getInterfaceType() {
        return interfaceType;
    }

    /**
     * Gets the device's operation environment.
     * @return an instance of {@link OperationEnvironment}
     */
    public OperationEnvironment getOperationEnvironment() {
        return operationEnvironment;
    }

    /**
     * Gets the generic attributes at device-level.
     * @return a list of {@link GenericAttribute}
     */
    public List<GenericAttribute> getGenericAttributes() {
        return genericAttributes;
    }

    /**
     * Gets the configuration parameters.
     * @return a list of {@link ConfigurationValue}
     */
    public List<ConfigurationValue> getConfigurationInfo() {
        return configurationInfo;
    }

    /**
     * Gets the functional profiles.
     * @return a list of {@link FunctionalProfile}
     */
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
