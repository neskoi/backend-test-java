package com.fcamara.minhaVaga.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fcamara.minhaVaga.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserDtoUpdatePasswordRequest {

	@NotBlank(message = "A senha deve ser preenchida com ao menos 8 caracteres.")
	@Size(min = 8, message = "A senha deve conter ao menos 8 caracteres.")
	private String password;

	public UserDtoUpdatePasswordRequest(String password) {
		this.password = password;
	}

	public User update(User user) {
		if (this.password != null)
			user.setPassword(this.password);
		return user;
	};
}
