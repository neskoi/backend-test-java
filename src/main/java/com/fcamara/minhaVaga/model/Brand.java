package com.fcamara.minhaVaga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Brand {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
	
		@Column(nullable = false)
		private String brand;
}
