package com.fcamara.minhaVaga.dto.request;

import com.fcamara.minhaVaga.model.CarPark;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CarParkDtoPhoneRequest {
	private String phone;
	
	public CarParkDtoPhoneRequest(String phone) {
		this.phone = phone;
	}

	public CarPark updatePhone(CarPark carPark) {
		carPark.setPhone(this.phone);
		return carPark;
	}
}
