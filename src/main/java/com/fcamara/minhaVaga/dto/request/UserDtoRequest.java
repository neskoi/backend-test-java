package com.fcamara.minhaVaga.dto.request;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.fcamara.minhaVaga.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDtoRequest {

	@NotBlank(message = "É preciso informar um nome")
	private String name;

	@NotBlank(message = "A senha deve ser preenchida com ao menos 8 caracteres.")
	@Size(min = 8, message = "A senha deve conter ao menos 8 caracteres.")
	private String password;

	@NotBlank(message = "É preciso informar um CPF")
	@CPF(message = "CPF invalido.")
	private String cpf;

	@NotBlank(message = "É preciso informar um email valido.")
	@Email(message = "É preciso informar um email valido.")
	@Column(unique = true)
	private String email;

	public User convertToUser() {
		return new User(this.name, this.password, this.cpf, this.email);
	}
}
