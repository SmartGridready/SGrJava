package com.smartgridready.communicator.modbus.impl;

import com.smartgridready.ns.v0.ModbusInterfaceDescription;
import com.smartgridready.ns.v0.ModbusRtu;
import com.smartgridready.ns.v0.ModbusTcp;

import com.smartgridready.driver.api.modbus.DataBits;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.Parity;
import com.smartgridready.driver.api.modbus.StopBits;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;
import com.smartgridready.communicator.modbus.api.ModbusGatewayFactory;

import java.util.NoSuchElementException;

import com.smartgridready.communicator.common.helper.DriverFactoryLoader;
import com.smartgridready.communicator.modbus.api.ModbusGateway;
import com.smartgridready.communicator.modbus.helper.ModbusType;
import com.smartgridready.communicator.modbus.helper.ModbusUtil;


public class SGrModbusGatewayFactory implements ModbusGatewayFactory {

    @Override
    public ModbusGateway create(ModbusInterfaceDescription interfaceDescription) throws GenDriverException {
        // distinguish RTU or TCP protocol
        ModbusType modbusType = ModbusUtil.getModbusType(interfaceDescription);
        GenDriverAPI4Modbus transport;
        switch (modbusType) {
            case RTU:
                try {
                    transport = createRTUTransport(interfaceDescription.getModbusRtu());
                } catch (NoSuchElementException e) {
                    throw new GenDriverException("No Modbus factory implementation found on classpath");
                }
                break;
 
            case TCP:
                try {
                    transport = createRTUTransport(interfaceDescription.getModbusRtu());
                } catch (NoSuchElementException e) {
                    throw new GenDriverException("No Modbus factory implementation found on classpath");
                }
                transport = createTCPTransport(interfaceDescription.getModbusTcp());
                break;

            default:
                throw new GenDriverException(String.format("Unsupported Modbus type %s", modbusType));
        }

        String identifier = ModbusUtil.getModbusGatewayIdentifier(interfaceDescription);
        return new ModbusGateway(identifier, interfaceDescription, transport);
    }

    private GenDriverAPI4Modbus createRTUTransport(ModbusRtu rtu) throws NoSuchElementException {
        String serialPort = rtu.getPortName();
        int baudrate = ModbusUtil.getSerialBaudrate(rtu.getBaudRateSelected());
        Parity parity = ModbusUtil.getSerialParity(rtu.getParitySelected());
        DataBits dataBits = ModbusUtil.getSerialDataBits(rtu.getByteLenSelected());
        StopBits stopBits = ModbusUtil.getSerialStopBits(rtu.getStopBitLenSelected());

        GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getImplementation(GenDriverAPI4ModbusFactory.class);

        return factory.createRtuTransport(serialPort, baudrate, parity, dataBits, stopBits);
    }

    private GenDriverAPI4Modbus createTCPTransport(ModbusTcp tcp) throws NoSuchElementException {
        String tcpAddress = tcp.getAddress();
        int tcpPort = ModbusUtil.isNonEmptyString(tcp.getPort()) ? Integer.valueOf(tcp.getPort()) : ModbusUtil.DEFAULT_MODBUS_TCP_PORT;

        GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getImplementation(GenDriverAPI4ModbusFactory.class);

        return factory.createTcpTransport(tcpAddress, tcpPort);
    }
}
