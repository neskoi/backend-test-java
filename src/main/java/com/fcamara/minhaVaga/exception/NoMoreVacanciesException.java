package com.fcamara.minhaVaga.exception;

public class NoMoreVacanciesException extends RuntimeException {
	private static final long serialVersionUID = 8754372423905390551L;
	public NoMoreVacanciesException(String message) {
		super(message);
	}
}
