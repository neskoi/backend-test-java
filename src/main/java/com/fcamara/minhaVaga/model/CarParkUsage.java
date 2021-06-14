package com.fcamara.minhaVaga.model;


import java.math.BigDecimal;
import java.sql.Timestamp;

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
	
	public CarParkUsage(Timestamp entranceTime, Vacancy vacancy, Vehicle vehicle, TypeOfPayment typeOfPayment) {
		this.entraceTime = entranceTime;
		this.vacancy = vacancy;
		this.vehicle = vehicle;
		this.typeOfPayment = typeOfPayment;
		this.paidPrice = vacancy.getHourPrice();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Vehicle vehicle;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Vacancy vacancy;
	
	@Column(nullable = false)
	private Timestamp entraceTime;
	
	@Column
	private Timestamp exitTime;
	
	@Enumerated(EnumType.STRING)
	private TypeOfPayment typeOfPayment;
	
	@Column
	private BigDecimal paidPrice;
}	
