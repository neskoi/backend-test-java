package com.fcamara.minhaVaga.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CarParks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String cnpj;
	
	@Column(nullable = false)
	private String adress;
	
	@Column(nullable = false)
	private String phone;
	
	@Column(nullable = false)
	private BigDecimal hourPrice;
	
	@Column(nullable = false)
	private BigDecimal montlhyPrice;
	
	@Column(nullable = false)
	private int carVacancy;
	
	@Column(nullable = false)
	private int motorcycleVacancy;
}
