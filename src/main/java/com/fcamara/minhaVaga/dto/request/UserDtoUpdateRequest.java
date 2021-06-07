package com.fcamara.minhaVaga.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fcamara.minhaVaga.model.User;

import lombok.Getter;

@Getter
public class UserDtoUpdateRequest {

	@Email
	private String email;

	@Size(min = 8, message = "")
	private String password;

	public User update(User user) {
		if (this.email != null)
			user.setEmail(this.email);
		if (this.password != null)
			user.setPassword(this.password);
		return user;
	};
}
