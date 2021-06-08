package com.fcamara.minhaVaga.dto.request;

import com.fcamara.minhaVaga.model.CarPark;

import lombok.Getter;

@Getter
public class CarParkDtoPhoneRequest {
	private String phone;
	
	public CarPark updatePhone(CarPark carPark) {
		carPark.setPhone(this.phone);
		return carPark;
	}
}
