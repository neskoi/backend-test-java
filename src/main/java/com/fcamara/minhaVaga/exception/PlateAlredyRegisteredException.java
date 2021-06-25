package com.fcamara.minhaVaga.exception;

public class PlateAlredyRegisteredException extends RuntimeException {
	private static final long serialVersionUID = -6545771037519375266L;

	public PlateAlredyRegisteredException(String message) {
		super(message);
	}
}
