package com.fcamara.minhaVaga.model;


import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.Column;
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
@NoArgsConstructor
@Entity
public class CarParkUsage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Vehicle vehicle;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Vacancy vacancy;
	
	@Column(nullable = false)
	private ZonedDateTime entraceTime;
	
	@Column
	private ZonedDateTime exitTime;
	
	@Enumerated(EnumType.STRING)
	private TypeOfPayment typeOfPayment;
	
	@Column
	private BigDecimal basePaidPrice;
	
	public CarParkUsage(Vacancy vacancy, Vehicle vehicle, TypeOfPayment typeOfPayment) {
		this.entraceTime =  ZonedDateTime.now();
		this.vacancy = vacancy;
		this.vehicle = vehicle;
		this.typeOfPayment = typeOfPayment;
		this.basePaidPrice = vacancy.getPrice(typeOfPayment);
	}
	
	public void exit() {
		this.exitTime =  ZonedDateTime.now();
	}
	
}	
