package com.smartgridready.communicator.modbus.api;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.ns.v0.ModbusInterfaceDescription;

import jakarta.annotation.PreDestroy;

/**
 * Implements a Modbus "gateway" wrapper, allowing shared use of Modbus RTU serial connections.
 */
public class ModbusGateway {

    private static final Logger LOG = LoggerFactory.getLogger(ModbusGateway.class);

    private final String identifier;
    private final ModbusInterfaceDescription interfaceDescription;
    private final GenDriverAPI4Modbus transport;
    private final boolean shared;

    private final Set<String> connectedKeys;

    /**
     * Constructs a new instance.
     * @param identifier the unique identifier
     * @param interfaceDescription the Modbus interface specification
     * @param transport the wrapped driver instance
     * @param shared shared access allowed when true
     */
    public ModbusGateway(
        String identifier,
        ModbusInterfaceDescription interfaceDescription,
        GenDriverAPI4Modbus transport,
        boolean shared
    ) {
        this.identifier = identifier;
        this.interfaceDescription = interfaceDescription;
        this.transport = transport;
        this.shared = shared;

        this.connectedKeys = new HashSet<>();
    }

    /**
     * Constructs a new instance with no shared access.
     * @param identifier the unique identifier
     * @param interfaceDescription the Modbus interface specification
     * @param transport the wrapped driver instance
     */
    public ModbusGateway(
        String identifier,
        ModbusInterfaceDescription interfaceDescription,
        GenDriverAPI4Modbus transport
    ) {
        this(identifier, interfaceDescription, transport, false);
    }

    @PreDestroy
    private void onDestroy() {
        try {
            if (transport.isConnected()) transport.disconnect();
        } catch (GenDriverException e) {}
    }

    /**
     * Gets the interface specification.
     * @return an instance of {@link ModbusInterfaceDescription}
     */
    public ModbusInterfaceDescription getInterfaceDescription() {
        return interfaceDescription;
    }

    /**
     * Gets the wrapped driver instance.
     * @return an instance of {@link GenDriverAPI4Modbus}
     */
    public GenDriverAPI4Modbus getTransport() {
        return transport;
    }

    /**
     * Gets the unique identifier.
     * @return a string
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Tells if shared access is possible.
     * @return a boolean
     */
    public boolean isShared() {
        return shared;
    }

    /**
     * Tells if a specific device is connected.
     * @param key the unique identifier
     * @return a boolean
     */
    public synchronized boolean isConnected(String key) {
        return shared
            ? connectedKeys.contains(key) && transport.isConnected()
            : transport.isConnected();
    }

    /**
     * Connects a specific device.
     * @param key the unique identifier
     * @throws GenDriverException when connecting failed
     */
    public synchronized void connect(String key) throws GenDriverException {
        if (shared) {
            connectedKeys.add(key);
        }
        if (!transport.isConnected()) {
            connectDriver();
        }
    }

    private void connectDriver() throws GenDriverException {
        if (!transport.connect()) {
			var msg = "Connect to Modbus device failed";
			if (interfaceDescription.getModbusRtu() != null) {
				msg = String.format("Connect to Modbus device on port %s failed.", interfaceDescription.getModbusRtu().getPortName());
			} else if (interfaceDescription.getModbusTcp() != null) {
				msg = String.format("Connect to Modbus device on IP address=%s failed.", interfaceDescription.getModbusTcp().getAddress());
			}
			LOG.error(msg);
			throw new GenDriverException(msg);
		}
    }

    /**
     * Disconnects a specific device.
     * @param key the unique identifier
     * @throws GenDriverException when disconnect failed
     */
    public synchronized void disconnect(String key) throws GenDriverException {
        if (shared) {
            connectedKeys.remove(key);
            if (!connectedKeys.isEmpty()) {
                return;
            }
        }
        if (transport.isConnected()) {
            transport.disconnect();
        }
    }

    /**
     * Disconnect all devices.
     */
    public synchronized void disconnectAll() {
        connectedKeys.clear();
        if (transport.isConnected()) {
            try {
                transport.disconnect();
            } catch (GenDriverException e) {}
        }
    }

    @Override
    public int hashCode() {
        return identifier == null ? 0 : identifier.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (getClass() == other.getClass()) {
            return true;
        }
        
        final ModbusGateway otherInstance = (ModbusGateway) other;
        return (
            (identifier == null && otherInstance.identifier == null) ||
            (identifier != null && identifier.equals(otherInstance.identifier))
        );
    }
}
