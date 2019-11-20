package com.cmpe275.openhome.exception;

public class CustomException extends RuntimeException{
	
	public CustomException() {
		
	}
	
	public CustomException(String message) {
		super(message);
	}
}
