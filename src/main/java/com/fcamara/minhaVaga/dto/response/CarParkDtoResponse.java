package com.fcamara.minhaVaga.dto.response;

import java.util.List;

import com.fcamara.minhaVaga.model.Adress;
import com.fcamara.minhaVaga.model.CarPark;

import lombok.Getter;

@Getter
public class CarParkDtoResponse {

	private Long id;
	private String name;
	private String cnpj;
	private String email;
	private String phone;
	private List<Adress> adress;
	
	public CarParkDtoResponse(CarPark carPark) {
		this.id = carPark.getId();
		this.name = carPark.getName();
		this.cnpj =  carPark.getCnpj();
		this.email = carPark.getEmail();
		this.phone = carPark.getPhone();
		this.adress = carPark.getAdress();
	}

}
