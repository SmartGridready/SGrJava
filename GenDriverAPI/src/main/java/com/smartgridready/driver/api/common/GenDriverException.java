package com.smartgridready.driver.api.common;

/**
 * Implements the most generalized interface driver exception.
 */
public class GenDriverException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Construct with message.
	 * @param aMessage the error message
	 */
	public GenDriverException(String aMessage) {
		super (aMessage);
	}
	
	/**
	 * Construct with inner exception.
	 * @param aCause the inner exception
	 */
	public GenDriverException(Throwable aCause) {
		super (aCause);
	}
	
	/**
	 * Construct with message and inner exception.
	 * @param aMessage the error message
	 * @param aCause the inner exception
	 */
	public GenDriverException(String aMessage, Throwable aCause) {
		super(aMessage, aCause);
	}	
}
