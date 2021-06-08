package com.fcamara.minhaVaga.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Getter;

@Getter
public class Bcrypt {
	
	@Autowired
	PasswordEncoder bcrypt;
	
	private String password;
	private String encodedPassword;
	
	public Bcrypt(String password) {
		this.password = password;
	}
	
	public String encode() {
		encodedPassword = bcrypt.encode(this.password);
		return encodedPassword;
	}
}
