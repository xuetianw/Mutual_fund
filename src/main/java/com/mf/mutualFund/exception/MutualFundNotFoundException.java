package com.mf.mutualFund.exception;

public class MutualFundNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MutualFundNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MutualFundNotFoundException(String message) {
		super(message);
	}
	
	public MutualFundNotFoundException(Throwable cause) {
		super(cause);
	}
}
