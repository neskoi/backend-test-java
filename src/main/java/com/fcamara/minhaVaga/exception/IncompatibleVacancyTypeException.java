package com.fcamara.minhaVaga.exception;

public class IncompatibleVacancyTypeException extends RuntimeException {
	private static final long serialVersionUID = -4659487871055600640L;
	
	public IncompatibleVacancyTypeException(String message) {
		super(message);
	}
}
