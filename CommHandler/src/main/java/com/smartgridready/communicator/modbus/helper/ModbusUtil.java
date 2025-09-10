package com.smartgridready.communicator.modbus.helper;

import java.util.Map;
import java.util.Objects;

import com.smartgridready.ns.v0.ModbusInterfaceDescription;
import com.smartgridready.ns.v0.ModbusInterfaceSelection;
import com.smartgridready.ns.v0.ModbusRtu;
import com.smartgridready.ns.v0.ModbusTcp;
import com.smartgridready.utils.StringUtil;
import com.smartgridready.driver.api.modbus.DataBits;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.StopBits;
import com.smartgridready.driver.api.modbus.Parity;

/**
 * Utility methods for Modbus.
 */
public class ModbusUtil {

    /** Default TCP port. */
    public static final int DEFAULT_MODBUS_TCP_PORT = 502;
    /** Default connection timeout in milliseconds. */
    public static final int DEFAULT_MODBUS_TCP_TIMEOUT = 5000;

    /** Default baud rate. */
    public static final int DEFAULT_BAUDRATE = 9600;
    /** Default parity. */
    public static final Parity DEFAULT_PARITY = Parity.NONE;
    /** Default data bits. */
    public static final DataBits DEFAULT_DATABITS = DataBits.EIGHT;
    /** Default stop bits. */
    public static final StopBits DEFAULT_STOPBITS = StopBits.ONE;

    /** Default slave ID. */
    public static final short DEFAULT_SLAVE_ID = 0xff;
    
    private static final Map<String, DataBits> dataBitMap = Map.of(
        com.smartgridready.ns.v0.ByteLength.VALUE_1.value(), DataBits.SEVEN,
        com.smartgridready.ns.v0.ByteLength.VALUE_2.value(), DataBits.EIGHT
    );
    
    private static final Map<String, StopBits> stopBitMap = Map.of(
        com.smartgridready.ns.v0.StopBitLength.VALUE_1.value(), StopBits.ONE,
        com.smartgridready.ns.v0.StopBitLength.VALUE_2.value(), StopBits.ONE_AND_HALF,
        com.smartgridready.ns.v0.StopBitLength.VALUE_3.value(), StopBits.TWO
    );

    private static final Map<String, Parity> parityMap = Map.of(
        com.smartgridready.ns.v0.Parity.NONE.value(), Parity.NONE,
        com.smartgridready.ns.v0.Parity.EVEN.value(), Parity.EVEN,
        com.smartgridready.ns.v0.Parity.ODD.value(), Parity.ODD
    );

    private ModbusUtil() {}

    /**
     * Tells if the interface uses a serial connection.
     * @param interfaceDescription the interface specification
     * @return a boolean
     */
    public static boolean isSerial(ModbusInterfaceDescription interfaceDescription) {
        ModbusRtu rtu = interfaceDescription.getModbusRtu();
        return (
            (rtu != null) &&
            StringUtil.isNotEmpty(rtu.getPortName())
        );
    }

    /**
     * Tells if the interface uses a TCP/IP connection.
     * @param interfaceDescription the interface specification
     * @return a boolean
     */
    public static boolean isTcp(ModbusInterfaceDescription interfaceDescription) {
        ModbusTcp tcp = interfaceDescription.getModbusTcp();
        return (
            (tcp != null) &&
            StringUtil.isNotEmpty(tcp.getAddress())
        );
    }

    /**
     * Gets the Modbus device slave ID.
     * @param interfaceDescription the interface specification
     * @return a short
     */
    public static short getModbusSlaveId(ModbusInterfaceDescription interfaceDescription) {
        // distinguish between Serial and TCP
        short slaveId = DEFAULT_SLAVE_ID;
        boolean isSerial = isSerial(interfaceDescription);
        boolean isTcp = isTcp(interfaceDescription);
        if (isSerial) {
            ModbusRtu serial = interfaceDescription.getModbusRtu();
            if (StringUtil.isNotEmpty(serial.getSlaveAddr())) slaveId = Short.valueOf(serial.getSlaveAddr());
        }
        if (isTcp) {
            ModbusTcp tcp = interfaceDescription.getModbusTcp();
            if (StringUtil.isNotEmpty(tcp.getSlaveId())) slaveId = Short.valueOf(tcp.getSlaveId());
        }

        return slaveId;
    }

    /**
     * Gets the unique gateway identifier of a Modbus device.
     * Is the serial port name or a combination of IP address and port.
     * @param interfaceDescription the interface specification
     * @return a string
     * @throws GenDriverException when the interface type could not be correctly detected
     */
    public static String getModbusGatewayIdentifier(ModbusInterfaceDescription interfaceDescription) throws GenDriverException {
        // distinguish between Serial and TCP
        boolean isSerial = isSerial(interfaceDescription);
        boolean isTcp = isTcp(interfaceDescription);
        if (isSerial) {
            ModbusRtu rtu = interfaceDescription.getModbusRtu();
            String portName = rtu.getPortName();
            return String.format("serial:%s", portName);
        }
        if (isTcp) {
            ModbusTcp tcp = interfaceDescription.getModbusTcp();
            String address = tcp.getAddress();
            int port = StringUtil.isNotEmpty(tcp.getPort()) ? Integer.valueOf(tcp.getPort()) : DEFAULT_MODBUS_TCP_PORT;
            return String.format("tcp:%s:%d", address, port);
        }

        // cannot be none
        throw new GenDriverException("Could not get Modbus gateway identifier");
    }

    /**
     * Gets the data bits configuration.
     * @param dataBits the data bits as string
     * @return an instance of {@link DataBits}
     */
    public static DataBits getSerialDataBits(String dataBits) {
        return dataBitMap.getOrDefault(dataBits, DEFAULT_DATABITS);
    }

    /**
     * Gets the stop bits configuration.
     * @param stopBits the stop bits as string
     * @return an instance of {@link StopBits}
     */
    public static StopBits getSerialStopBits(String stopBits) {
        return stopBitMap.getOrDefault(stopBits, DEFAULT_STOPBITS);
    }

    /**
     * Gets the parity configuration.
     * @param parity the parity as string
     * @return an instance of {@link Parity}
     */
    public static Parity getSerialParity(String parity) {
        return parityMap.getOrDefault(parity, DEFAULT_PARITY);
    }

    /**
     * Gets the serial baud rate.
     * @param baudRate the baud rate as string
     * @return an int
     */
    public static int getSerialBaudrate(String baudRate) {
        return StringUtil.isNotEmpty(baudRate) ? Integer.valueOf(baudRate) : DEFAULT_BAUDRATE;
    }

    /**
     * Tells if interface specifications of two devices match.
     * @param interface1 the interface specification of device 1
     * @param interface2 the interface specification of device 2
     * @return a boolean
     */
    public static boolean interfaceParametersMatch(ModbusInterfaceDescription interface1, ModbusInterfaceDescription interface2) {
        if (
            (null == interface1.getModbusRtu() && null != interface2.getModbusRtu()) ||
            (null != interface1.getModbusRtu() && null == interface2.getModbusRtu())
        ) {
            return false;
        } else if (null != interface1.getModbusRtu() && null != interface2.getModbusRtu()) {
            ModbusRtu rtu1 = interface1.getModbusRtu();
            ModbusRtu rtu2 = interface2.getModbusRtu();
            if (
                !(
                    Objects.equals(rtu1.getPortName(), rtu2.getPortName()) &&
                    Objects.equals(rtu1.getBaudRateSelected(), rtu2.getBaudRateSelected()) &&
                    Objects.equals(rtu1.getByteLenSelected(), rtu2.getByteLenSelected()) &&
                    Objects.equals(rtu1.getParitySelected(), rtu2.getParitySelected()) &&
                    Objects.equals(rtu1.getStopBitLenSelected(), rtu2.getStopBitLenSelected())
                )
            ) {
                return false;
            }
        }

        if (
            (null == interface1.getModbusTcp() && null != interface2.getModbusTcp()) ||
            (null != interface1.getModbusTcp() && null == interface2.getModbusTcp())
        ) {
            return false;
        } else if (null != interface1.getModbusTcp() && null != interface2.getModbusTcp()) {
            ModbusTcp tcp1 = interface1.getModbusTcp();
            ModbusTcp tcp2 = interface2.getModbusTcp();
            if (
                !(
                    Objects.equals(tcp1.getAddress(), tcp2.getAddress()) &&
                    Objects.equals(tcp1.getPort(), tcp2.getPort())
                )
            ) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the Modbus interface type.
     * @param interfaceDescription the interface specification
     * @return an instance of {@link ModbusType}
     */
    public static ModbusType getModbusType(ModbusInterfaceDescription interfaceDescription) {
        ModbusInterfaceSelection modbusType = interfaceDescription.getModbusInterfaceSelection();

        // does not support ASCII
        switch (modbusType) {
            case TCPIP:
                return ModbusType.TCP;

            case RTU:
                return ModbusType.RTU;
            
            case RTU_ASCII:
                return ModbusType.RTU_ASCII;

            case UDPIP:
                return ModbusType.UDP;
            
            case RTU_TCPIP:
                return ModbusType.RTU_TCP;

            default:
                return ModbusType.UNKNOWN;
        }
    }
}
