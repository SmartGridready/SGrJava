package com.smartgridready.driver.api.modbus;

/**
 * Implements a Modbus interface driver exception.
 */
public class GenDriverModbusException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construct with message and inner exception.
	 * @param aMessage the error message
	 * @param aCause the inner exception
	 */
	public GenDriverModbusException(String aMessage, Throwable aCause) {
		super(aMessage, aCause);
	}

	/**
	 * Construct with message.
	 * @param aMessage the error message
	 */
	public GenDriverModbusException(String aMessage) {
		super(aMessage);
	}

	/**
	 * Construct with inner exception.
	 * @param aCause the inner exception
	 */
	public GenDriverModbusException(Throwable aCause) {
		super(aCause);
	}
}
