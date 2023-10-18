package com.mfu.wallet.exception;

public class InsufficientFundsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InsufficientFundsException(String message, Throwable cause) {
		super(message,cause);
	}
	
	public InsufficientFundsException(String message) {
		super(message);
	}
	
	public InsufficientFundsException(Throwable cause) {
		super(cause);
	}
	

}
