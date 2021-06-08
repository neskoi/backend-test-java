package com.fcamara.minhaVaga.dto.request;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fcamara.minhaVaga.model.CarPark;

import lombok.Getter;

@Getter
public class CarParkDtoRequest {

	@NotBlank(message = "É preciso informar o nome do estabelecimento.")
	private String name;

	@NotBlank(message = "A senha deve ser preenchida com ao menos 8 caracteres.")
	@Size(min = 8, message = "A senha deve conter ao menos 8 caracteres.")
	private String password;

	@NotBlank(message = "É preciso informar um CNPJ.")
	@Pattern(regexp = "[\\d]{14,14}", message = "O CNPJ deve conter apenas números")
	@CNPJ(message = "É preciso fornecer um CNPJ valido")
	private String cnpj;

	@NotBlank(message = "É preciso informar um email valido.")
	@Email(message = "É preciso informar um email valido.")
	private String email;

	@Pattern(regexp = "[\\d]{10,11}", message = "O telefone deve conter apenas números")
	private String phone;

	public CarPark convertToCarPark() {
		return new CarPark(this.name, this.password, this.cnpj, this.email, this.phone);
	}

}
