package com.fcamara.minhaVaga.exception;

public class InvalidUserException extends RuntimeException {
	private static final long serialVersionUID = -378852961508590689L;
	public InvalidUserException (String message) {
		super(message);
	}
}
