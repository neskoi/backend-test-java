package com.fcamara.minhaVaga.model;

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
public class MonthlyUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private CarPark carPark;
	
	@Column(nullable = false)
	private Timestamp paidOn;
	
	@Column(nullable = false)
	private BigDecimal paidValue;
}
