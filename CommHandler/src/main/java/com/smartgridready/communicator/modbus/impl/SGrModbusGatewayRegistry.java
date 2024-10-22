package com.smartgridready.communicator.modbus.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import com.smartgridready.ns.v0.ModbusInterfaceDescription;

import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.communicator.modbus.api.ModbusGatewayFactory;
import com.smartgridready.communicator.modbus.api.ModbusGatewayRegistry;
import com.smartgridready.communicator.modbus.api.ModbusGateway;
import com.smartgridready.communicator.modbus.helper.ModbusUtil;

public class SGrModbusGatewayRegistry implements ModbusGatewayRegistry {

    private static final Logger LOG = LoggerFactory.getLogger(SGrModbusGatewayRegistry.class);

    private final ModbusGatewayFactory modbusGatewayFactory;
    private final Map<String, ModbusGateway> gateways;
    private final Map<ModbusGateway, Integer> gatewayRefCount;

    public SGrModbusGatewayRegistry(ModbusGatewayFactory modbusGatewayFactory) {
        this.modbusGatewayFactory = modbusGatewayFactory;
        gateways = new HashMap<>();
        gatewayRefCount = new HashMap<>();
    }

    public SGrModbusGatewayRegistry() {
        this(new SGrModbusGatewayFactory());
    }

    @Override
    public synchronized ModbusGateway attachGateway(ModbusInterfaceDescription interfaceDescription) throws GenDriverException {

        String gatewayIdentifier = ModbusUtil.getModbusGatewayIdentifier(interfaceDescription);

        ModbusGateway gatewayInstance = gateways.get(gatewayIdentifier);
        if (gatewayInstance != null) {
            // verify that interface parameters match
            if (!ModbusUtil.interfaceParametersMatch(interfaceDescription, gatewayInstance.getInterfaceDescription())) {
                throw new GenDriverException("Interface parameters do not match");
            }

            // use existing gateway
            Integer refCount = gatewayRefCount.getOrDefault(gatewayInstance, Integer.valueOf(0));
            refCount = refCount.intValue() + 1;
            gatewayRefCount.put(gatewayInstance, refCount);

            LOG.info("Attached Modbus gateway '{}', refcount={}", gatewayIdentifier, refCount);
        } else {
            // create and register new gateway
            gatewayInstance = modbusGatewayFactory.create(interfaceDescription);
            gateways.put(gatewayIdentifier, gatewayInstance);
            gatewayRefCount.put(gatewayInstance, Integer.valueOf(1));

            LOG.info("Registered Modbus gateway '{}'", gatewayIdentifier);
        }

        return gatewayInstance;
    }

    @Override
    public void detachGateway(String identifier) throws GenDriverException {
        detachGatewayInternal(identifier);
    }

    @Override
    public void detachGateway(ModbusInterfaceDescription interfaceDescription) throws GenDriverException {
        detachGatewayInternal(ModbusUtil.getModbusGatewayIdentifier(interfaceDescription));
    }

    private synchronized void detachGatewayInternal(String identifier) {
        ModbusGateway gatewayInstance = gateways.get(identifier);
        if (gatewayInstance != null) {
            Integer refCount = gatewayRefCount.get(gatewayInstance);
            if (refCount != null) {
                refCount = refCount.intValue() - 1;
                gatewayRefCount.put(gatewayInstance, refCount);
                if (refCount <= 0) {
                    // disconnect gateway and remove from registry
                    try {
                        gatewayInstance.getTransport().disconnect();
                    } catch (GenDriverException e) {
                        LOG.error("Error disconnecting Modbus gateway: {}", e.getMessage());
                    }
                    
                    gatewayRefCount.remove(gatewayInstance);
                    gateways.remove(identifier);

                    LOG.info("Removed Modbus gateway '{}'", identifier);
                } else {
                    LOG.info("Detached Modbus gateway '{}', refcount={}", identifier, refCount);
                }
            } else {
                gateways.remove(identifier);

                LOG.info("Removed Modbus gateway '{}'", identifier);
            }
        }
    }

    @Override
    public synchronized void detachAllGateways() {
        for (ModbusGateway gw: gateways.values()) {
            try {
                gw.getTransport().disconnect();
            } catch (Exception e) {}
        }

        gateways.clear();
        gatewayRefCount.clear();
        
        LOG.info("Removed all Modbus gateways");
    }

    @Override
    public List<String> getAllGatewayIdentifiers() {
        return gateways.values().stream().map(ModbusGateway::getIdentifier).collect(Collectors.toList());
    }
}
