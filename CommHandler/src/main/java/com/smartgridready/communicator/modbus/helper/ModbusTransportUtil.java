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

public class ModbusTransportUtil {
    
    public static GenDriverAPI4Modbus createTransport(ModbusInterfaceDescription interfaceDescription, GenDriverAPI4ModbusFactory factory) throws GenDriverException {
		if (factory == null) {
            throw new GenDriverException("No Modbus factory implementation found on classpath");
        }

        // distinguish RTU or TCP protocol
        ModbusType modbusType = ModbusUtil.getModbusType(interfaceDescription);
        switch (modbusType) {
            case RTU:
                return createRtuTransport(interfaceDescription.getModbusRtu(), factory);
 
            case TCP:
                return createTcpTransport(interfaceDescription.getModbusTcp(), factory);

            default:
                throw new GenDriverException(String.format("Unsupported Modbus type %s", modbusType));
        }
	}

	private static GenDriverAPI4Modbus createRtuTransport(ModbusRtu rtu, GenDriverAPI4ModbusFactory factory) throws GenDriverException {
		if (rtu == null) {
			throw new GenDriverException("No Modbus RTU configuration found");
		}

		String serialPort = rtu.getPortName();
		int baudrate = ModbusUtil.getSerialBaudrate(rtu.getBaudRateSelected());
		Parity parity = ModbusUtil.getSerialParity(rtu.getParitySelected());
		DataBits dataBits = ModbusUtil.getSerialDataBits(rtu.getByteLenSelected());
		StopBits stopBits = ModbusUtil.getSerialStopBits(rtu.getStopBitLenSelected());

		return factory.createRtuTransport(serialPort, baudrate, parity, dataBits, stopBits);
	}

	private static GenDriverAPI4Modbus createTcpTransport(ModbusTcp tcp, GenDriverAPI4ModbusFactory factory) throws GenDriverException {
		if (tcp == null) {
			throw new GenDriverException("No Modbus TCP configuration found");
		}

		String tcpAddress = tcp.getAddress();
        int tcpPort = ModbusUtil.isNonEmptyString(tcp.getPort()) ? Integer.valueOf(tcp.getPort()) : ModbusUtil.DEFAULT_MODBUS_TCP_PORT;

		return factory.createTcpTransport(tcpAddress, tcpPort);
	}
}
