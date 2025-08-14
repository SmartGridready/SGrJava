package com.smartgridready.communicator.modbus.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartgridready.ns.v0.ModbusInterfaceDescription;

import jakarta.annotation.PreDestroy;

import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;
import com.smartgridready.communicator.modbus.api.ModbusGatewayRegistry;
import com.smartgridready.communicator.modbus.api.ModbusGateway;
import com.smartgridready.communicator.modbus.helper.ModbusTransportUtil;
import com.smartgridready.communicator.modbus.helper.ModbusUtil;

/**
 * Default implementation of a shared Modbus gateway registry.
 * Keeps track of multiple devices attached to the same transport.
 */
public class SGrModbusGatewayRegistry implements ModbusGatewayRegistry {

    private static final Logger LOG = LoggerFactory.getLogger(SGrModbusGatewayRegistry.class);

    private static ModbusGatewayRegistry defaultInstance;

    private final Map<String, ModbusGateway> gateways;
    private final Map<ModbusGateway, Set<String>> gatewayAttachedKeys;

    /**
     * Constructs a new instance.
     */
    public SGrModbusGatewayRegistry() {
        gateways = new HashMap<>();
        gatewayAttachedKeys = new HashMap<>();
    }

    @PreDestroy
    private void onDestroy() {
        detachAllGateways();
    }

    /**
     * Gets the default singleton instance of the Modbus gateway registry.
     * @return an instance of {@link SGrModbusGatewayRegistry}
     */
    public static synchronized ModbusGatewayRegistry defaultInstance() {
        if (defaultInstance == null) defaultInstance = new SGrModbusGatewayRegistry();
        return defaultInstance;
    }

    @Override
    public synchronized ModbusGateway attachGateway(
        ModbusInterfaceDescription interfaceDescription,
        GenDriverAPI4ModbusFactory driverFactory,
        String key
    ) throws GenDriverException {

        String gatewayIdentifier = ModbusUtil.getModbusGatewayIdentifier(interfaceDescription);

        ModbusGateway gatewayInstance = gateways.get(gatewayIdentifier);
        if (gatewayInstance != null) {
            // verify that interface parameters match
            if (!ModbusUtil.interfaceParametersMatch(interfaceDescription, gatewayInstance.getInterfaceDescription())) {
                throw new GenDriverException("Interface parameters do not match");
            }
        } else {
            // create and register new gateway
            String identifier = ModbusUtil.getModbusGatewayIdentifier(interfaceDescription);
            GenDriverAPI4Modbus transport = ModbusTransportUtil.createTransport(interfaceDescription, driverFactory);
            gatewayInstance = new ModbusGateway(identifier, interfaceDescription, transport, true);
            gateways.put(gatewayIdentifier, gatewayInstance);
        }

        // use existing gateway
        Set<String> attachedKeys = gatewayAttachedKeys.getOrDefault(gatewayInstance, new HashSet<>());
        attachedKeys.add(key);
        gatewayAttachedKeys.put(gatewayInstance, attachedKeys);

        LOG.info("Attached Modbus gateway '{}', refcount={}", gatewayIdentifier, attachedKeys.size());

        return gatewayInstance;
    }

    @Override
    public void detachGateway(String identifier, String key) throws GenDriverException {
        detachGatewayInternal(identifier, key);
    }

    @Override
    public void detachGateway(ModbusInterfaceDescription interfaceDescription, String key) throws GenDriverException {
        detachGatewayInternal(ModbusUtil.getModbusGatewayIdentifier(interfaceDescription), key);
    }

    private synchronized void detachGatewayInternal(String identifier, String key) {
        ModbusGateway gatewayInstance = gateways.get(identifier);
        if (gatewayInstance != null) {
            Set<String> attachedKeys = gatewayAttachedKeys.get(gatewayInstance);
            if (attachedKeys != null) {
                attachedKeys.remove(key);
                if (attachedKeys.isEmpty()) {
                    gateways.remove(identifier);
                    gatewayInstance.disconnectAll();
                }
            } else {
                gateways.remove(identifier);
                gatewayAttachedKeys.remove(gatewayInstance);
                LOG.info("Removed Modbus gateway '{}'", identifier);
            }
        } else {
            LOG.info("Modbus gateway '{}' not registered", identifier);
        }
    }

    @Override
    public synchronized void detachAllGateways() {
        for (ModbusGateway gw: gateways.values()) {
            gw.disconnectAll();
        }

        gateways.clear();
        
        LOG.info("Removed all Modbus gateways");
    }

    @Override
    public List<String> getAllGatewayIdentifiers() {
        return gateways.values().stream().map(ModbusGateway::getIdentifier).collect(Collectors.toList());
    }
}
