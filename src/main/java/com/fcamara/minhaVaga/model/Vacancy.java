package com.fcamara.minhaVaga.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
public class Vacancy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "adress_id", nullable = false)
	private Adress adress;

	@Enumerated(EnumType.STRING)
	private TypeOfVehicle typeOfVehicle;

	@NotBlank(message = "A quantia de vagas deve ser preenchida")
	private int amount;

	@NotBlank(message = "O valor do custo hora deve ser preenchido")
	private BigDecimal hourPrice;

	@NotBlank(message = "O valor do custo dia deve ser preenchido")
	private BigDecimal dayPrice;

	@NotBlank(message = "O valor do custo mês deve ser preenchido")
	private BigDecimal monthPrice;

}
