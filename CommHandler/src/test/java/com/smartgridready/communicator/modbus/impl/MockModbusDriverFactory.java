package com.smartgridready.communicator.modbus.impl;

import com.smartgridready.driver.api.modbus.DataBits;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;
import com.smartgridready.driver.api.modbus.Parity;
import com.smartgridready.driver.api.modbus.StopBits;

public class MockModbusDriverFactory implements GenDriverAPI4ModbusFactory {

    @Override
    public GenDriverAPI4Modbus createRtuTransport(String portName) {
        return new GenDriverAPI4ModbusRTUMock();
    }

    @Override
    public GenDriverAPI4Modbus createRtuTransport(String portName, int baudRate) {
        return new GenDriverAPI4ModbusRTUMock();
    }

    @Override
    public GenDriverAPI4Modbus createRtuTransport(String portName, int baudRate, Parity parity) {
        return new GenDriverAPI4ModbusRTUMock();
    }

    @Override
    public GenDriverAPI4Modbus createRtuTransport(String portName, int baudRate, Parity parity, DataBits dataBits) {
        return new GenDriverAPI4ModbusRTUMock();
    }

    @Override
    public GenDriverAPI4Modbus createRtuTransport(String portName, int baudRate, Parity parity, DataBits dataBits, StopBits stopBits) {
        return new GenDriverAPI4ModbusRTUMock();
    }

    @Override
    public GenDriverAPI4Modbus createTcpTransport(String ipAddress) {
        return new GenDriverAPI4ModbusRTUMock();
    }

    @Override
    public GenDriverAPI4Modbus createTcpTransport(String ipAddress, int port) {
        return new GenDriverAPI4ModbusRTUMock();
    }

    @Override
    public GenDriverAPI4Modbus createTcpTransport(String ipAddress, int port, int timeout) {
        return new GenDriverAPI4ModbusRTUMock();
    }

    @Override
    public GenDriverAPI4Modbus createTcpTransport(String ipAddress, int port, int timeout, boolean rtuOverTcp) {
        return new GenDriverAPI4ModbusRTUMock();
    }

    @Override
    public GenDriverAPI4Modbus createUdpTransport(String ipAddress) {
        return new GenDriverAPI4ModbusRTUMock();
    }

    @Override
    public GenDriverAPI4Modbus createUdpTransport(String ipAddress, int port) {
        return new GenDriverAPI4ModbusRTUMock();
    }
}
