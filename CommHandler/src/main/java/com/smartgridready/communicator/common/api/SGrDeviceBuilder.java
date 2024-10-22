package com.smartgridready.communicator.common.api;

import java.util.Properties;

import java.io.InputStream;
import java.nio.file.Path;

import com.smartgridready.communicator.messaging.client.HiveMqtt5MessagingClientFactory;
import com.smartgridready.driver.api.messaging.GenMessagingClientFactory;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.InterfaceList;
import com.smartgridready.ns.v0.ModbusInterfaceDescription;

import com.smartgridready.communicator.common.api.dto.InterfaceType;
import com.smartgridready.communicator.common.helper.DeviceDescriptionLoader;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.communicator.messaging.impl.SGrMessagingDevice;
import com.smartgridready.communicator.modbus.api.ModbusGatewayFactory;
import com.smartgridready.communicator.modbus.api.ModbusGatewayRegistry;
import com.smartgridready.communicator.modbus.impl.SGrModbusGatewayFactory;
import com.smartgridready.communicator.modbus.impl.SGrModbusDevice;
import com.smartgridready.communicator.rest.exception.RestApiAuthenticationException;
import com.smartgridready.communicator.rest.http.client.ApacheHttpRequestFactory;
import com.smartgridready.driver.api.http.GenHttpRequestFactory;
import com.smartgridready.communicator.rest.impl.SGrRestApiDevice;

/**
 * Generic SGr device builder.
 */
public class SGrDeviceBuilder {
    
    private EidSource eidSource;
    private Properties properties;

    private GenHttpRequestFactory httpClientFactory;
    private ModbusGatewayFactory modbusGatewayFactory;
    private GenMessagingClientFactory messagingClientFactory;

    private ModbusGatewayRegistry modbusGatewayRegistry;

    public SGrDeviceBuilder() {
        this.eidSource = null;
        this.properties = null;
        this.modbusGatewayRegistry = null;

        // default implementations
        this.httpClientFactory = new ApacheHttpRequestFactory();
        this.modbusGatewayFactory = new SGrModbusGatewayFactory();
        this.messagingClientFactory = new HiveMqtt5MessagingClientFactory();
    }

    /**
     * Sets the EID source to a file system path.
     * @param path the file system path
     * @return the same instance of the builder object
     */
    public SGrDeviceBuilder eid(Path path) {
        this.eidSource = new PathEidSource(path);
        return this;
    }

    /**
     * Sets the EID source to an input stream.
     * @param inputStream the input stream
     * @return the same instance of the builder object
     */
    public SGrDeviceBuilder eid(InputStream inputStream) {
        this.eidSource = new StreamEidSource(inputStream);
        return this;
    }

    /**
     * Sets the EID source to XML content.
     * @param content the XML content
     * @return the same instance of the builder object
     */
    public SGrDeviceBuilder eid(String content) {
        this.eidSource = new TextEidSource(content);
        return this;
    }

    /**
     * Sets the REST API service client factory.
     * @param httpClientFactory an instance of a REST API service client factory
     * @return the same instance of the builder object
     */
    public SGrDeviceBuilder useRestServiceClientFactory(GenHttpRequestFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
        return this;
    }

    /**
     * Sets the Modbus gateway factory.
     * @param modbusGatewayFactory an instance of a Modbus gateway factory
     * @return the same instance of the builder object
     */
    public SGrDeviceBuilder useModbusGatewayFactory(ModbusGatewayFactory modbusGatewayFactory) {
        this.modbusGatewayFactory = modbusGatewayFactory;
        return this;
    }

    /**
     * Sets the messaging client factory to be used
     * @param messagingClientFactory the messaging client factory to be used
     */
    public SGrDeviceBuilder useMessagingClientFactory(GenMessagingClientFactory messagingClientFactory) {
        this.messagingClientFactory = messagingClientFactory;
        return this;
    }


    /**
     * Sets the shared Modbus gateway registry.
     * @param modbusGatewayRegistry an instance of a Modbus gateway registry
     * @return the same instance of the builder object
     */
    public SGrDeviceBuilder useSharedModbusGatewayRegistry(ModbusGatewayRegistry modbusGatewayRegistry) {
        this.modbusGatewayRegistry = modbusGatewayRegistry;
        return this;
    }

    /**
     * Sets the EID properties.
     * @param properties the properties
     * @return the same instance of the builder object
     */
    public SGrDeviceBuilder properties(Properties properties) {
        this.properties = properties;
        return this;
    }

    /**
     * Builds the SGr device using the previously set builder parameters.
     * @return an SGr device object
     * @throws GenDriverException on generic error
     * @throws RestApiAuthenticationException on authentication error
     */
    public GenDeviceApi build() throws GenDriverException, RestApiAuthenticationException {
        if (eidSource == null) {
            throw new GenDriverException("No EID defined");
        }

        DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
        
        DeviceFrame deviceFrame = eidSource.load(loader, properties);
        if (deviceFrame == null) {
            throw new GenDriverException("EID could not be loaded");
        }

        InterfaceType interfaceType = getInterfaceType(deviceFrame.getInterfaceList());
        switch (interfaceType) {
            case MODBUS:
                ModbusInterfaceDescription interfaceDescription = deviceFrame.getInterfaceList().getModbusInterface().getModbusInterfaceDescription();
                if (interfaceDescription == null) {
                    throw new GenDriverException("No Modbus interface description defined");
                }
                if (modbusGatewayRegistry != null) {
                    // use shared modbus gateway registry
                    return new SGrModbusDevice(deviceFrame, modbusGatewayRegistry);
                } else if (modbusGatewayFactory != null) {
                    // use modbus factory directly
                    return new SGrModbusDevice(deviceFrame, modbusGatewayFactory);
                }
                throw new GenDriverException("No Modbus gateway registry or factory defined");

            case RESTAPI:
                if (httpClientFactory == null) {
                    throw new GenDriverException("No REST API service client factory defined");
                }
                return new SGrRestApiDevice(deviceFrame, httpClientFactory);

            case MESSAGING:
                return new SGrMessagingDevice(deviceFrame, messagingClientFactory);

            default:
                throw new GenDriverException(String.format("Unsupported interface type %s", interfaceType));
        }
    }

    private static InterfaceType getInterfaceType(InterfaceList interfaceList) {

        if (null != interfaceList.getModbusInterface()) {
            return InterfaceType.MODBUS;
        }

        if (null != interfaceList.getRestApiInterface()) {
            return InterfaceType.RESTAPI;
        }

        if (null != interfaceList.getMessagingInterface()) {
            return InterfaceType.MESSAGING;
        }

        if (null != interfaceList.getContactInterface()) {
            return InterfaceType.CONTACT;
        }

        if (null != interfaceList.getGenericInterface()) {
            return InterfaceType.GENERIC;
        }

        return InterfaceType.UNKNOWN;
    }

    /**
     * An interface for EID data sources.
     */
    private interface EidSource {
    
        DeviceFrame load(DeviceDescriptionLoader loader, Properties properties);
    }

    private static class PathEidSource implements EidSource {

        private final Path path;

        public PathEidSource(Path path) {
            this.path = path;
        }

        @Override
        public DeviceFrame load(DeviceDescriptionLoader loader, Properties properties) {
            String directory = path.getParent().toString();
            String fileName = path.getFileName().toString();
            return loader.load(directory, fileName, properties);
        }
    }

    private static class StreamEidSource implements EidSource {

        private final InputStream inputStream;

        public StreamEidSource(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public DeviceFrame load(DeviceDescriptionLoader loader, Properties properties) {
            return loader.load("eid.xml", inputStream, properties);
        }
    }

    private static class TextEidSource implements EidSource {

        private final String content;

        public TextEidSource(String content) {
            this.content = content;
        }

        @Override
        public DeviceFrame load(DeviceDescriptionLoader loader, Properties properties) {
            return loader.load(content, properties);
        }
    }
}
