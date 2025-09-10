package com.smartgridready.driver.api.modbus;

/**
 * Implements a Modbus interface driver exception.
 * Contains Modbus exception code.
 */
public class GenDriverModbusException extends Exception {

    private static final long serialVersionUID = 1L;

    /** The exception enumeration. */
    private final ModbusExceptionType exceptionType;
    /** The numeric exception code. */
    private final int exceptionCode;

    /**
     * Construct with message and inner exception.
     * @param aMessage the error message
     * @param aCause the inner exception
     * @param exceptionCode the numeric exception code
     */
    public GenDriverModbusException(String aMessage, Throwable aCause, int exceptionCode) {
        super(aMessage, aCause);
        this.exceptionCode = exceptionCode;
        this.exceptionType = ModbusExceptionType.fromCode(exceptionCode);
    }

    /**
     * Construct with message and inner exception.
     * @param aMessage the error message
     * @param aCause the inner exception
     */
    public GenDriverModbusException(String aMessage, Throwable aCause) {
        this(aMessage, aCause, 0xFF);
    }

    /**
     * Construct with message.
     * @param aMessage the error message
     * @param exceptionCode the numeric exception code
     */
    public GenDriverModbusException(String aMessage, int exceptionCode) {
        super(aMessage);
        this.exceptionCode = exceptionCode;
        this.exceptionType = ModbusExceptionType.fromCode(exceptionCode);
    }

    /**
     * Construct with message.
     * @param aMessage the error message
     */
    public GenDriverModbusException(String aMessage) {
        this(aMessage, 0xFF);
    }

    /**
     * Construct with inner exception.
     * @param aCause the inner exception
     * @param exceptionCode the numeric exception code
     */
    public GenDriverModbusException(Throwable aCause, int exceptionCode) {
        super(aCause);
        this.exceptionCode = exceptionCode;
        this.exceptionType = ModbusExceptionType.fromCode(exceptionCode);
    }

    /**
     * Construct with inner exception.
     * @param aCause the inner exception
     */
    public GenDriverModbusException(Throwable aCause) {
        this(aCause, 0xFF);
    }

    /**
     * Gets the numeric exception code.
     * @return an integer
     */
    public int getExceptionCode() {
        return exceptionCode;
    }

    /**
     * Gets the exception type.
     * @return an instance of {@link ModbusExceptionType}
     */
    public ModbusExceptionType getExceptionType() {
        return exceptionType;
    }
}
