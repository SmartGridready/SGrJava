package com.smartgridready.communicator.common.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Provides an EID information DTO.
 */
public class ProductInfo {
    
    private String identifier;
    private String releaseState;
    private String testState;
    private String manufacturer;
    private String productName;
    private String productType;
    // cannot use the name 'interface', is reserved
    @JsonProperty("interface")
    private String interfaceType;
    private String level;
    private String version;
    private String swRevision;
    private String hwRevision;

    /**
     * Constructs.
     */
    public ProductInfo() {}

    /**
     * Gets the EID identifier.
     * @return a string
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the EID identifier.
     * @param identifier the EID identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the release state.
     * @return a string
     */
    public String getReleaseState() {
        return releaseState;
    }

    /**
     * Sets the release state.
     * @param releaseState the release state
     */
    public void setReleaseState(String releaseState) {
        this.releaseState = releaseState;
    }

    /**
     * Gets the test state.
     * @return a string
     */
    public String getTestState() {
        return testState;
    }

    /**
     * Sets the test state.
     * @param testState the test state
     */
    public void setTestState(String testState) {
        this.testState = testState;
    }

    /**
     * Gets the manufacturer name.
     * @return a string
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets the manufacturer name.
     * @param manufacturer the manufacturer name
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Gets the product name.
     * @return a string
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the product name.
     * @param productName the product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets the product type.
     * @return a string
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Sets the product type.
     * @param productType the product type
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * Gets the communication interface type.
     * @return a string
     */
    public String getInterface() {
        return interfaceType;
    }

    /**
     * Sets the communication interface type.
     * @param interfaceType the interface type as string
     */
    public void setInterface(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    /**
     * Gets the SGr level.
     * @return a string
     */
    public String getLevel() {
        return level;
    }

    /**
     * Sets the SGr level.
     * @param level the level as string
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * Gets the EID version.
     * @return a string
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the EID version.
     * @param version the version string
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets the product software version.
     * @return a string
     */
    public String getSwRevision() {
        return swRevision;
    }

    /**
     * Sets the product software version.
     * @param swRevision the version string
     */
    public void setSwRevision(String swRevision) {
        this.swRevision = swRevision;
    }

    /**
     * Gets the product hardware version.
     * @return a string
     */
    public String getHwRevision() {
        return hwRevision;
    }

    /**
     * Sets the product hardware version.
     * @param hwRevision the version string
     */
    public void setHwRevision(String hwRevision) {
        this.hwRevision = hwRevision;
    }
}
