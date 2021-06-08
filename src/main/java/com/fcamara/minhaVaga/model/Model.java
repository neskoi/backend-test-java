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

@Data
@Entity
public class Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String model;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Brand brand;
	
	@Enumerated(EnumType.STRING)
	private TypeOfVehicle type;
}
