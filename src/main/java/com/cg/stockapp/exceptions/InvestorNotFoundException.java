package com.cg.stockapp.exceptions;

public class InvestorNotFoundException extends RuntimeException {
	
	private String operation;
	
	public InvestorNotFoundException(String operation, String message) {
		super(message);
		this.operation = operation;
	}
	
	public String getOperation() {
		return this.operation;
	}
}
