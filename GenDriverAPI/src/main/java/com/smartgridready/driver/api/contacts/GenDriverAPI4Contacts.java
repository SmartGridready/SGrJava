package com.smartgridready.driver.api.contacts;

import com.smartgridready.driver.api.common.GenDriverException;

/**
 * Defines the interface of a digital contacts interface driver.
 */
public interface GenDriverAPI4Contacts {

    /**
     * Connects to the contacts interface. 
     * @return true if connected, false otherwise
     * @throws GenDriverException when connection failed
     */
    boolean connect() throws GenDriverException;

    /**
     * Disconnects from the contacts interface.
     * @throws GenDriverException when an error occurred
     */
    void disconnect() throws GenDriverException;

    /**
     * Tells if the contacts interface is connected.
     * @return true if connected, false otherwise
     */
    boolean isConnected();

    /**
     * Reads the state of the digital contacts according to
     * a specific functional profile and data point.
     * The number of contacts returned is determined by the driver instance.
     * @param functionalProfileName the functional profile name
     * @param dataPointName the data point name
     * @return an array of boolean
     * @throws GenDriverException when an error occurred
     */
    boolean[] readContacts(String functionalProfileName, String dataPointName) throws GenDriverException;

    /**
     * Writes the state of the digital contacts according to
     * a specific functional profile and data point.
     * If the given number of values is less than the driver instance's,
     * only the first n contacts are written.
     * @param functionalProfileName the functional profile name
     * @param dataPointName the data point name
     * @param values an array of boolean containing the states to write
     * @throws GenDriverException when an error occurred
     */
    void writeContacts(String functionalProfileName, String dataPointName, boolean[] values) throws GenDriverException;
}
