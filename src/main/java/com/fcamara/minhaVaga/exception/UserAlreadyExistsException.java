package com.fcamara.minhaVaga.exception;

public class UserAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = -5906559371047720331L;
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}

}
