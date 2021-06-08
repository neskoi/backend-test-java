package com.fcamara.minhaVaga.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fcamara.minhaVaga.model.User;

import lombok.Getter;

@Getter
public class UserDtoUpdateEmailRequest {

	@NotBlank(message = "É preciso informar um email valido.")
	@Email(message = "É preciso informar um email valido.")
	private String email;

	public User update(User user) {
		if (this.email != null)
			user.setEmail(this.email);
		return user;
	};
}
