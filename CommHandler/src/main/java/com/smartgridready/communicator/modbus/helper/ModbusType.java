package com.smartgridready.communicator.modbus.helper;

/**
 * Defines types of Modbus transports.
 */
public enum ModbusType {
    /** Unknown, not supported. */
    UNKNOWN,
    /** Modbus RTU over serial. */
    RTU,
    /** Modbus TCP. */
    TCP,
    /** Modbus UDP. */
    UDP,
    /** Modbus RTU over serial, with ASCII encoding. */
    RTU_ASCII
}
