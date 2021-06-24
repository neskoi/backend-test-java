package com.fcamara.minhaVaga.exception;

public class VehicleAlreadyParkedException extends RuntimeException {
	private static final long serialVersionUID = -5617977384693286987L;
	public VehicleAlreadyParkedException(String message) {
		super(message);
	}
}
