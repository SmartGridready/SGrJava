package com.smartgridready.driver.api.modbus;

import com.smartgridready.driver.api.common.GenDriverException;

public interface GenDriverAPI4Modbus {

    boolean connect() throws GenDriverException;

    void disconnect() throws GenDriverException;

    boolean isConnected();
	
    @Deprecated
	void setUnitIdentifier(short unitId);
	
    @Deprecated
    int[] ReadInputRegisters(int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;
    
    @Deprecated
    int[] ReadHoldingRegisters(int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;
    
    @Deprecated
    boolean[] ReadDiscreteInputs(int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;

    @Deprecated
    boolean[] ReadCoils(int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;
    
    @Deprecated
    void WriteMultipleCoils(int startingAdress, boolean[] values) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;
    
    @Deprecated
    void WriteSingleCoil(int startingAdress, boolean value) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;
     
    @Deprecated
    void WriteMultipleRegisters(int startingAdress, int[] values) throws GenDriverException, GenDriverSocketException, GenDriverModbusException; 
    
    @Deprecated
    void WriteSingleRegister(int startingAdress, int value) throws GenDriverException, GenDriverSocketException, GenDriverModbusException;

    default int[] readInputRegisters(short unitId, int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        return ReadInputRegisters(startingAddress, quantity);
    }

    default int[] readHoldingRegisters(short unitId, int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        return ReadHoldingRegisters(startingAddress, quantity);
    }

    default boolean[] readDiscreteInputs(short unitId, int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        return ReadDiscreteInputs(startingAddress, quantity);
    }

    default boolean[] readCoils(short unitId, int startingAddress, int quantity) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        return ReadCoils(startingAddress, quantity);
    }

    default void writeMultipleRegisters(short unitId, int startingAddress, int[] values) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        WriteMultipleRegisters(startingAddress, values);
    }

    default void writeSingleRegister(short unitId, int startingAddress, int value) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        WriteSingleRegister(startingAddress, value);
    }

    default void writeMultipleCoils(short unitId, int startingAddress, boolean[] values) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        WriteMultipleCoils(startingAddress, values);
    }

    default void writeSingleCoil(short unitId, int startingAddress, boolean value) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        setUnitIdentifier(unitId);
        WriteSingleCoil(startingAddress, value);
    }
}