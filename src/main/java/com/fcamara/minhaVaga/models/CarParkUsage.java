package com.fcamara.minhaVaga.models;


import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class CarParkUsage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Users user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private CarParks carpark;
	
	@Column(nullable = false)
	private Timestamp entraceTimestamp;
	
	@Column
	private Timestamp exitTimestamp;
	
	@Column
	private BigDecimal pricePaidPerHour;
}	
