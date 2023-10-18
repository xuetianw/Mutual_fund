package com.mfu.wallet.exception;

public class CurrencyFormatException  extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CurrencyFormatException(String message, Throwable cause) {
		super(message,cause);
	}
	
	public CurrencyFormatException(String message) {
		super(message);
	}
	
	public CurrencyFormatException(Throwable cause) {
		super(cause);
	} 
}


