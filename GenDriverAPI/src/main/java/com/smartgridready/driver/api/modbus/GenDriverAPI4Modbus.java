package com.smartgridready.driver.api.modbus;

import com.smartgridready.driver.api.common.GenDriverException;

/**
 * Defines the interface of a Modbus interface driver.
 */
public interface GenDriverAPI4Modbus {

    /**
     * Connects to the Modbus interface. 
     * @return true if connected, false otherwise
     * @throws GenDriverException when connection failed
     */
    boolean connect() throws GenDriverException;

    /**
     * Disconnects from the Modbus interface.
     * @throws GenDriverException when an error occurred
     */
    void disconnect() throws GenDriverException;

    /**
     * Tells if the Modbus interface is connected.
     * @return true if connected, false otherwise
     */
    boolean isConnected();

    /**
     * Sets the Modbus unit identifier / slave ID for future commands.
     * This method is deprecated, as the current implementation prefers sending the unit ID with each command.
     * @param unitId the new unit ID
     */
    @Deprecated
	void setUnitIdentifier(short unitId);
	
    /**
     * Reads one or multiple input registers.
     * This method is deprecated, as the current implementation prefers sending the unit ID with each command. Use {@code readInputRegisters} instead.
     * @param startingAddress the first register address to read
     * @param quantity the number of registers to read
     * @return an array of integers
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    @Deprecated
    int[] ReadInputRegisters(int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;
    
    /**
     * Reads one or multiple holding registers.
     * This method is deprecated, as the current implementation prefers sending the unit ID with each command. Use {@code readHoldingRegisters} instead.
     * @param startingAddress the first register address to read
     * @param quantity the number of registers to read
     * @return an array of integers
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    @Deprecated
    int[] ReadHoldingRegisters(int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;

    /**
     * Reads one or multiple discrete inputs.
     * This method is deprecated, as the current implementation prefers sending the unit ID with each command. Use {@code readDiscreteInputs} instead.
     * @param startingAddress the first discrete input address to read
     * @param quantity the number of discrete inputs to read
     * @return an array of boolean
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    @Deprecated
    boolean[] ReadDiscreteInputs(int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;

    /**
     * Reads one or multiple coils.
     * This method is deprecated, as the current implementation prefers sending the unit ID with each command. Use {@code readCoils} instead.
     * @param startingAddress the first coil address to read
     * @param quantity the number of coils to read
     * @return an array of boolean
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    @Deprecated
    boolean[] ReadCoils(int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;

    /**
     * Writes multiple coils.
     * This method is deprecated, as the current implementation prefers sending the unit ID with each command. Use {@code writeMultipleCoils} instead.
     * @param startingAdress the first coil address to write
     * @param values the array of coil values to write
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    @Deprecated
    void WriteMultipleCoils(int startingAdress, boolean[] values) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;

    /**
     * Writes a single coil.
     * This method is deprecated, as the current implementation prefers sending the unit ID with each command. Use {@code writeSingleCoil} instead.
     * @param startingAdress the coil address to write
     * @param value the coil value to write
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    @Deprecated
    void WriteSingleCoil(int startingAdress, boolean value) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;

    /**
     * Writes multiple holding registers.
     * This method is deprecated, as the current implementation prefers sending the unit ID with each command. Use {@code writeMultipleRegisters} instead.
     * @param startingAdress the first register address to write
     * @param values the array of register values to write
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    @Deprecated
    void WriteMultipleRegisters(int startingAdress, int[] values) throws GenDriverException, GenDriverSocketException, GenDriverModbusException; 

    /**
     * Writes a single holding register.
     * This method is deprecated, as the current implementation prefers sending the unit ID with each command. Use {@code writeSingleRegister} instead.
     * @param startingAdress the register address to write
     * @param value the register value to write
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    @Deprecated
    void WriteSingleRegister(int startingAdress, int value) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;

    /**
     * Reads one or multiple input registers.
     * @param unitId the unit identifier
     * @param startingAddress the first register address to read
     * @param quantity the number of registers to read
     * @return an array of integers
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    default int[] readInputRegisters(short unitId, int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        return ReadInputRegisters(startingAddress, quantity);
    }

    /**
     * Reads one or multiple holding registers.
     * @param unitId the unit identifier
     * @param startingAddress the first register address to read
     * @param quantity the number of registers to read
     * @return an array of integers
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    default int[] readHoldingRegisters(short unitId, int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        return ReadHoldingRegisters(startingAddress, quantity);
    }

    /**
     * Reads one or multiple discrete inputs.
     * @param unitId the unit identifier
     * @param startingAddress the first discrete input address to read
     * @param quantity the number of discrete inputs to read
     * @return an array of boolean
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    default boolean[] readDiscreteInputs(short unitId, int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        return ReadDiscreteInputs(startingAddress, quantity);
    }

    /**
     * Reads one or multiple coils.
     * @param unitId the unit identifier
     * @param startingAddress the first coil address to read
     * @param quantity the number of coils to read
     * @return an array of boolean
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    default boolean[] readCoils(short unitId, int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        return ReadCoils(startingAddress, quantity);
    }

    /**
     * Writes multiple holding registers.
     * @param unitId the unit identifier
     * @param startingAddress the first register address to write
     * @param values the array of register values to write
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    default void writeMultipleRegisters(short unitId, int startingAddress, int[] values) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        WriteMultipleRegisters(startingAddress, values);
    }

    /**
     * Writes a single holding register.
     * @param unitId the unit identifier
     * @param startingAddress the register address to write
     * @param value the register value to write
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    default void writeSingleRegister(short unitId, int startingAddress, int value) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        WriteSingleRegister(startingAddress, value);
    }

    /**
     * Writes multiple coils.
     * @param unitId the unit identifier
     * @param startingAddress the first coil address to write
     * @param values the array of coil values to write
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    default void writeMultipleCoils(short unitId, int startingAddress, boolean[] values) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        WriteMultipleCoils(startingAddress, values);
    }

    /**
     * Writes a single coil.
     * @param unitId the unit identifier
     * @param startingAddress the coil address to write
     * @param value the coil value to write
     * @throws GenDriverException when a general error occurred
     * @throws GenDriverSocketException when a network error occurred
     * @throws GenDriverModbusException when a Modbus protocol error occurred
     */
    default void writeSingleCoil(short unitId, int startingAddress, boolean value) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        WriteSingleCoil(startingAddress, value);
    }
}