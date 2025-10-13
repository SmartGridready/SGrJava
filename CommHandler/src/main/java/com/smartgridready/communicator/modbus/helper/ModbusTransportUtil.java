package com.smartgridready.communicator.modbus.helper;

import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.DataBits;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;
import com.smartgridready.driver.api.modbus.Parity;
import com.smartgridready.driver.api.modbus.StopBits;
import com.smartgridready.ns.v0.ModbusInterfaceDescription;
import com.smartgridready.ns.v0.ModbusRtu;
import com.smartgridready.ns.v0.ModbusTcp;
import com.smartgridready.utils.StringUtil;

/**
 * A helper class to create instances of Modbus transport connections.
 */
public class ModbusTransportUtil {

    private ModbusTransportUtil() {}

    /**
     * Creates a new tranport.
     * @param interfaceDescription the interface specification
     * @param factory the driver factory
     * @return an instance of {@link GenDriverAPI4Modbus}
     * @throws GenDriverException when the interface type is not supported
     */
    public static GenDriverAPI4Modbus createTransport(ModbusInterfaceDescription interfaceDescription, GenDriverAPI4ModbusFactory factory) throws GenDriverException {
        if (factory == null) {
            throw new GenDriverException("No Modbus factory implementation found on classpath");
        }

        // distinguish RTU or TCP protocol
        ModbusType modbusType = ModbusUtil.getModbusType(interfaceDescription);
        switch (modbusType) {
            case RTU:
                return createRtuTransport(interfaceDescription.getModbusRtu(), false, factory);

            case RTU_ASCII:
                return createRtuTransport(interfaceDescription.getModbusRtu(), true, factory);

            case TCP:
                return createTcpTransport(interfaceDescription.getModbusTcp(), factory);

            case RTU_TCP:
                return createRtuTcpTransport(interfaceDescription.getModbusTcp(), factory);

            case UDP:
                return createUdpTransport(interfaceDescription.getModbusTcp(), factory);

            default:
                throw new GenDriverException(String.format("Unsupported Modbus type %s", modbusType));
        }
    }

    private static GenDriverAPI4Modbus createRtuTransport(ModbusRtu rtu, boolean asciiEncoding, GenDriverAPI4ModbusFactory factory) throws GenDriverException {
        if (rtu == null) {
            throw new GenDriverException("No Modbus RTU configuration found");
        }

        String serialPort = rtu.getPortName();
        int baudrate = ModbusUtil.getSerialBaudrate(rtu.getBaudRateSelected());
        Parity parity = ModbusUtil.getSerialParity(rtu.getParitySelected());
        DataBits dataBits = ModbusUtil.getSerialDataBits(rtu.getByteLenSelected());
        StopBits stopBits = ModbusUtil.getSerialStopBits(rtu.getStopBitLenSelected());

        return factory.createRtuTransport(serialPort, baudrate, parity, dataBits, stopBits, asciiEncoding);
    }

    private static GenDriverAPI4Modbus createTcpTransport(ModbusTcp tcp, GenDriverAPI4ModbusFactory factory) throws GenDriverException {
        if (tcp == null) {
            throw new GenDriverException("No Modbus TCP configuration found");
        }

        String tcpAddress = tcp.getAddress();
        int tcpPort = StringUtil.isNotEmpty(tcp.getPort()) ? Integer.valueOf(tcp.getPort()) : ModbusUtil.DEFAULT_MODBUS_TCP_PORT;
        int timeout = (null != tcp.getTimeout()) ? Integer.valueOf(tcp.getTimeout()) : ModbusUtil.DEFAULT_MODBUS_TCP_TIMEOUT;

        return factory.createTcpTransport(tcpAddress, tcpPort, timeout, false);
    }

    private static GenDriverAPI4Modbus createRtuTcpTransport(ModbusTcp tcp, GenDriverAPI4ModbusFactory factory) throws GenDriverException {
        if (tcp == null) {
            throw new GenDriverException("No Modbus TCP configuration found");
        }

        String tcpAddress = tcp.getAddress();
        int tcpPort = StringUtil.isNotEmpty(tcp.getPort()) ? Integer.valueOf(tcp.getPort()) : ModbusUtil.DEFAULT_MODBUS_TCP_PORT;
        int timeout = (null != tcp.getTimeout()) ? Integer.valueOf(tcp.getTimeout()) : ModbusUtil.DEFAULT_MODBUS_TCP_TIMEOUT;

        return factory.createTcpTransport(tcpAddress, tcpPort, timeout, true);
    }

    private static GenDriverAPI4Modbus createUdpTransport(ModbusTcp udp, GenDriverAPI4ModbusFactory factory) throws GenDriverException {
        if (udp == null) {
            throw new GenDriverException("No Modbus UDP configuration found");
        }

        String udpAddress = udp.getAddress();
        int udpPort = StringUtil.isNotEmpty(udp.getPort()) ? Integer.valueOf(udp.getPort()) : ModbusUtil.DEFAULT_MODBUS_TCP_PORT;

        return factory.createUdpTransport(udpAddress, udpPort);
    }
}
