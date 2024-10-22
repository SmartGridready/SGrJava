package com.smartgridready.driver.api.modbus;

public interface GenDriverAPI4ModbusFactory {
    
    public GenDriverAPI4Modbus createRtuTransport(String comPort);

    public GenDriverAPI4Modbus createRtuTransport(String comPort, int baudRate);

    public GenDriverAPI4Modbus createRtuTransport(String comPort, int baudRate, Parity parity);

    public GenDriverAPI4Modbus createRtuTransport(String comPort, int baudRate, Parity parity, DataBits dataBits);

    public GenDriverAPI4Modbus createRtuTransport(String comPort, int baudRate, Parity parity, DataBits dataBits, StopBits stopBits);

    public GenDriverAPI4Modbus createTcpTransport(String ipAddress);

    public GenDriverAPI4Modbus createTcpTransport(String ipAddress, int port);
}
