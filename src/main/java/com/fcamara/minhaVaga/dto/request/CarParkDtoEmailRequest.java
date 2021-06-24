package com.fcamara.minhaVaga.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fcamara.minhaVaga.model.CarPark;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CarParkDtoEmailRequest {
	
	@NotBlank(message = "É preciso informar um email valido.")
	@Email(message = "É preciso informar um email valido.")
	private String email;
	
	public CarParkDtoEmailRequest(String email) {
		this.email = email;
	}

	public CarPark updateEmail(CarPark carPark) {
		carPark.setEmail(this.email);
		return carPark;
	}

}
