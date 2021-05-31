package com.fcamara.minhaVaga.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
public class Models {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String model;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Brands brand;
	
	@Column(nullable = false)
	private boolean isCar;
}
