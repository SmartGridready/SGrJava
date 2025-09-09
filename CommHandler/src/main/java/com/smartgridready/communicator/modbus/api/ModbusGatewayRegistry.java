package com.smartgridready.communicator.modbus.api;

import java.util.List;

import com.smartgridready.ns.v0.ModbusInterfaceDescription;

import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;


/**
 * An interface for a Modbus gateway registry.
 */
public interface ModbusGatewayRegistry {

    /**
     * Uses or creates a Modbus transport gateway for a given device.
     * @param interfaceDescription the device's Modbus interface description
     * @param driverFactory the Modbus driver factory
     * @param key the unique device object key
     * @return a Modbus gateway instance
     * @throws GenDriverException when the gateway could not be attached
     */
    public ModbusGateway attachGateway(
        ModbusInterfaceDescription interfaceDescription,
        GenDriverAPI4ModbusFactory driverFactory,
        String key
    ) throws GenDriverException;

    /**
     * Disconnects the Modbus transport gateway of a given device.
     * @param interfaceDescription the device's Modbus interface description
     * @param key the unique device object key
     * @throws GenDriverException when no gateway could be detached
     */
    public void detachGateway(ModbusInterfaceDescription interfaceDescription, String key) throws GenDriverException;

    /**
     * Disconnects the Modbus transport gateway of a given device.
     * @param identifier the transport identifier
     * @param key the unique device object key
     * @throws GenDriverException when no gateway could be detached
     */
    public void detachGateway(String identifier, String key) throws GenDriverException;

    /**
     * Disconnects all Modbus transport gateways.
     */
    public void detachAllGateways();

    /**
     * Gets all transport identifiers.
     * @return a list of strings
     */
    public List<String> getAllGatewayIdentifiers();
}
