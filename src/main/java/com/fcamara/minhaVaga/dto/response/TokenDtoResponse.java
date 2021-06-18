package com.fcamara.minhaVaga.dto.response;

import lombok.Getter;

@Getter
public class TokenDtoResponse {

	private String token;
	private String type;
	
	public TokenDtoResponse(String token, String type) {
		this.token = token;
		this.type = type;
	}
}
