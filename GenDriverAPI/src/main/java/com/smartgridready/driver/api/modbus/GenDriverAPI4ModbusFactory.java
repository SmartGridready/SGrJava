package com.smartgridready.driver.api.modbus;

/**
 * Defines the interface for a Modbus interface driver factory.
 */
public interface GenDriverAPI4ModbusFactory {

    /**
     * Creates a serial Modbus RTU transport.
     * @param comPort the serial port name
     * @return a new instance of {@link GenDriverAPI4Modbus}
     */
    public GenDriverAPI4Modbus createRtuTransport(String comPort);

    /**
     * Creates a serial Modbus RTU transport.
     * @param comPort the serial port name
     * @param baudRate the serial port baud rate
     * @return a new instance of {@link GenDriverAPI4Modbus}
     */
    public GenDriverAPI4Modbus createRtuTransport(String comPort, int baudRate);

    /**
     * Creates a serial Modbus RTU transport.
     * @param comPort the serial port name
     * @param baudRate the serial port baud rate
     * @param parity the serial port parity
     * @return a new instance of {@link GenDriverAPI4Modbus}
     */
    public GenDriverAPI4Modbus createRtuTransport(String comPort, int baudRate, Parity parity);

    /**
     * Creates a serial Modbus RTU transport.
     * @param comPort the serial port name
     * @param baudRate the serial port baud rate
     * @param parity the serial port parity
     * @param dataBits the serial port data bits
     * @return a new instance of {@link GenDriverAPI4Modbus}
     */
    public GenDriverAPI4Modbus createRtuTransport(String comPort, int baudRate, Parity parity, DataBits dataBits);

    /**
     * Creates a serial Modbus RTU transport.
     * @param comPort the serial port name
     * @param baudRate the serial port baud rate
     * @param parity the serial port parity
     * @param dataBits the serial port data bits
     * @param stopBits the serial port stop bits
     * @return a new instance of {@link GenDriverAPI4Modbus}
     */
    public GenDriverAPI4Modbus createRtuTransport(String comPort, int baudRate, Parity parity, DataBits dataBits, StopBits stopBits);

    /**
     * Creates a serial Modbus RTU transport.
     * @param comPort the serial port name
     * @param baudRate the serial port baud rate
     * @param parity the serial port parity
     * @param dataBits the serial port data bits
     * @param stopBits the serial port stop bits
     * @param asciiEncoding use ASCII encoding if true, otherwise RTU encoding
     * @return a new instance of {@link GenDriverAPI4Modbus}
     */
    default GenDriverAPI4Modbus createRtuTransport(String comPort, int baudRate, Parity parity, DataBits dataBits, StopBits stopBits, boolean asciiEncoding) {
        return createRtuTransport(comPort, baudRate, parity, dataBits, stopBits);
    }

    /**
     * Creates a Modbus TCP transport. Uses the default port 502.
     * @param ipAddress the IP address or host name
     * @return a new instance of {@link GenDriverAPI4Modbus}
     */
    public GenDriverAPI4Modbus createTcpTransport(String ipAddress);

    /**
     * Creates a Modbus TCP transport.
     * @param ipAddress the IP address or host name
     * @param port the TCP port
     * @return a new instance of {@link GenDriverAPI4Modbus}
     */
    public GenDriverAPI4Modbus createTcpTransport(String ipAddress, int port);

    /**
     * Creates a Modbus UDP transport. Uses the default port 502.
     * @param ipAddress the IP address or host name
     * @return a new instance of {@link GenDriverAPI4Modbus}
     */
    public GenDriverAPI4Modbus createUdpTransport(String ipAddress);

    /**
     * Creates a Modbus UDP transport.
     * @param ipAddress the IP address or host name
     * @param port the UDP port
     * @return a new instance of {@link GenDriverAPI4Modbus}
     */
    public GenDriverAPI4Modbus createUdpTransport(String ipAddress, int port);
}
