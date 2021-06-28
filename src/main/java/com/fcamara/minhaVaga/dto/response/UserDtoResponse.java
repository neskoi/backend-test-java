package com.fcamara.minhaVaga.dto.response;

import java.util.List;

import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.model.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDtoResponse {
	
	private Long id;
	private String name;
	private String cpf;
	private String email;
	private List<Vehicle> vehicle;
	
	public UserDtoResponse(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.cpf = user.getCpf();
		this.email = user.getEmail();
		this.vehicle = user.getVehicle();
	}
}
