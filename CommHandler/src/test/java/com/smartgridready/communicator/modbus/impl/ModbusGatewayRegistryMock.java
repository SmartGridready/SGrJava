package com.smartgridready.communicator.modbus.impl;

import java.util.List;
import java.util.Collections;

import com.smartgridready.ns.v0.ModbusInterfaceDescription;

import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;
import com.smartgridready.communicator.modbus.api.ModbusGatewayRegistry;
import com.smartgridready.communicator.modbus.api.ModbusGateway;

/**
 * Implements a mock of a Modbus gateway registry.
 * Only for testing purposes.
 */
public class ModbusGatewayRegistryMock implements ModbusGatewayRegistry {

    private final ModbusGateway mock;

    /**
     * Constructs a new instance.
     */
    public ModbusGatewayRegistryMock() {
        mock = new ModbusGateway(null, null, new GenDriverAPI4ModbusRTUMock());
    }

    public void setIsIntegerType(boolean returnInteger) {
        ((GenDriverAPI4ModbusRTUMock) mock.getTransport()).setIsIntegerType(returnInteger);
    }

    @Override
    public ModbusGateway attachGateway(ModbusInterfaceDescription interfaceDescription, GenDriverAPI4ModbusFactory driverFactory, String key)
            throws GenDriverException {
        return mock;
    }

    @Override
    public void detachGateway(ModbusInterfaceDescription interfaceDescription, String key) throws GenDriverException {
        // nothing
    }

    @Override
    public void detachGateway(String identifier, String key) throws GenDriverException {
        // nothing
    }

    @Override
    public void detachAllGateways() {
        // nothing
    }

    @Override
    public List<String> getAllGatewayIdentifiers() {
        return Collections.singletonList("rtu:mock");
    }
}
