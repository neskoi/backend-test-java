package com.fcamara.minhaVaga.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Model {
	
	public Model(String model, Brand brand, TypeOfVehicle typeOfVehicle) {
		super();
		this.model = model;
		this.brand = brand;
		this.typeOfVehicle = typeOfVehicle;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String model;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Brand brand;
	
	@Enumerated(EnumType.STRING)
	private TypeOfVehicle typeOfVehicle;
}
