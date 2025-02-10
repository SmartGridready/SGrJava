package com.smartgridready.communicator.modbus.helper;

import java.util.Map;
import java.util.Objects;

import com.smartgridready.ns.v0.ModbusInterfaceDescription;
import com.smartgridready.ns.v0.ModbusInterfaceSelection;
import com.smartgridready.ns.v0.ModbusRtu;
import com.smartgridready.ns.v0.ModbusTcp;

import com.smartgridready.driver.api.modbus.DataBits;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.StopBits;
import com.smartgridready.driver.api.modbus.Parity;

public class ModbusUtil {

    public static final int DEFAULT_MODBUS_TCP_PORT = 502;
    
    public static final int DEFAULT_BAUDRATE = 9600;
    public static final Parity DEFAULT_PARITY = Parity.NONE;
    public static final DataBits DEFAULT_DATABITS = DataBits.EIGHT;
    public static final StopBits DEFAULT_STOPBITS = StopBits.ONE;

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

    public static boolean isSerial(ModbusInterfaceDescription interfaceDescription) {
        ModbusRtu rtu = interfaceDescription.getModbusRtu();
        return (
            (rtu != null) &&
            isNonEmptyString(rtu.getPortName())
        );
    }

    public static boolean isTcp(ModbusInterfaceDescription interfaceDescription) {
        ModbusTcp tcp = interfaceDescription.getModbusTcp();
        return (
            (tcp != null) &&
            isNonEmptyString(tcp.getAddress())
        );
    }

    public static short getModbusSlaveId(ModbusInterfaceDescription interfaceDescription) {
        // distinguish between Serial and TCP
        short slaveId = DEFAULT_SLAVE_ID;
        boolean isSerial = isSerial(interfaceDescription);
        boolean isTcp = isTcp(interfaceDescription);
        if (isSerial) {
            ModbusRtu serial = interfaceDescription.getModbusRtu();
            if (isNonEmptyString(serial.getSlaveAddr())) slaveId = Short.valueOf(serial.getSlaveAddr());
        }
        if (isTcp) {
            ModbusTcp tcp = interfaceDescription.getModbusTcp();
            if (isNonEmptyString(tcp.getSlaveId())) slaveId = Short.valueOf(tcp.getSlaveId());
        }

        return slaveId;
    }

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
            int port = isNonEmptyString(tcp.getPort()) ? Integer.valueOf(tcp.getPort()) : DEFAULT_MODBUS_TCP_PORT;
            return String.format("tcp:%s:%d", address, port);
        }

        // cannot be none
        throw new GenDriverException("Could not get Modbus gateway identifier");
    }

    public static DataBits getSerialDataBits(String dataBits) {
        return dataBitMap.getOrDefault(dataBits, DEFAULT_DATABITS);
    }

    public static StopBits getSerialStopBits(String stopBits) {
        return stopBitMap.getOrDefault(stopBits, DEFAULT_STOPBITS);
    }

    public static Parity getSerialParity(String parity) {
        return parityMap.getOrDefault(parity, DEFAULT_PARITY);
    }

    public static int getSerialBaudrate(String baudRate) {
        return isNonEmptyString(baudRate) ? Integer.valueOf(baudRate) : DEFAULT_BAUDRATE;
    }

    public static boolean isNonEmptyString(String value) {
        return (
            (value != null) &&
            !value.isEmpty()
        );
    }

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

            default:
                return ModbusType.UNKNOWN;
        }
    }
}
