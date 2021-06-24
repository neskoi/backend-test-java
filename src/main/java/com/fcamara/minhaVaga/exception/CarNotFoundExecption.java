package com.fcamara.minhaVaga.exception;

public class CarNotFoundExecption extends RuntimeException {
	private static final long serialVersionUID = 3015954813888831091L;
	public CarNotFoundExecption(String message) {
		super(message);
	}
}
