package com.mf.mutualFund.exception;

public class MutualFundDetailsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MutualFundDetailsNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MutualFundDetailsNotFoundException(String message) {
		super(message);
	}
	
	public MutualFundDetailsNotFoundException(Throwable cause) {
		super(cause);
	}
}
