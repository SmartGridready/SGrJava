package com.smartgridready.driver.api.modbus;

/**
 * Implements an interface driver network socket exception.
 */
public class GenDriverSocketException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construct with message and inner exception.
	 * @param aMessage the error message
	 * @param aCause the inner exception
	 */
	public GenDriverSocketException(String aMessage, Throwable aCause) {
		super(aMessage, aCause);
	}

	/**
	 * Construct with message.
	 * @param aMessage the error message
	 */
	public GenDriverSocketException(String aMessage) {
		super(aMessage);
	}

	/**
	 * Construct with inner exception.
	 * @param aCause the inner exception
	 */
	public GenDriverSocketException(Throwable aCause) {
		super(aCause);
	}
}
