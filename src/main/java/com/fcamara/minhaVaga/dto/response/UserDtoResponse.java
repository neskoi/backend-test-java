package com.fcamara.minhaVaga.dto.response;

import com.fcamara.minhaVaga.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDtoResponse {
	private Long id;
	private String name;
	private String cpf;
	private String email;
	
	public UserDtoResponse(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.cpf = user.getCpf();
		this.email = user.getEmail();
	}
}
