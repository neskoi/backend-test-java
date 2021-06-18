package com.fcamara.minhaVaga.dto.request;

import javax.validation.constraints.NotBlank;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;

@Getter
public class LoginFormDto {

	@NotBlank
	private String email; 
	
	@NotBlank
	private String password;
	
	public UsernamePasswordAuthenticationToken convertToAuthToken() {
		return new UsernamePasswordAuthenticationToken(this.email, this.password);
	}
}
