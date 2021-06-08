package com.fcamara.minhaVaga.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fcamara.minhaVaga.model.CarPark;

import lombok.Getter;

@Getter
public class CarParkDtoPasswordRequest {
	
	@NotBlank(message = "A senha deve ser preenchida com ao menos 8 caracteres.")
	@Size(min = 8, message = "A senha deve conter ao menos 8 caracteres.")
	private String password;
	
	public CarPark updatePassword(CarPark carPark) {
		carPark.setPassword(this.password);
		return carPark;
	}
}
