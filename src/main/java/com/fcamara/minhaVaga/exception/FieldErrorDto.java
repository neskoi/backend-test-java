package com.fcamara.minhaVaga.exception;

import lombok.Getter;

@Getter
public class FieldErrorDto {
	
	private String field;
	private String message;
	
	public FieldErrorDto(String field, String error) {
		this.field = field;
		this.message = error;
	}
	
}
