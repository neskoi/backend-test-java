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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Vacancy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "adress_id", nullable = false)
	@JsonBackReference
	private Adress adress;
	
	@Enumerated(EnumType.STRING)
	private TypeOfVehicle typeOfVehicle;

	private int amount;

	private BigDecimal hourPrice;

	private BigDecimal dayPrice;

	private BigDecimal monthPrice;

	public Vacancy(Adress adress, TypeOfVehicle typeOfVehicle, int amount, BigDecimal hourPrice, BigDecimal dayPrice,
			BigDecimal monthPrice) {
		this.adress = adress;
		this.typeOfVehicle = typeOfVehicle;
		this.amount = amount;
		this.hourPrice = hourPrice;
		this.dayPrice = dayPrice;
		this.monthPrice = monthPrice;
	}

	public void update(Vacancy vacancyInfo) {
		this.typeOfVehicle = vacancyInfo.getTypeOfVehicle();
		this.amount = vacancyInfo.getAmount();
		this.hourPrice = vacancyInfo.getHourPrice();
		this.dayPrice = vacancyInfo.getDayPrice();
		this.monthPrice = vacancyInfo.getMonthPrice();
	}

	//Isso gera um acoplamento não muito intressante. como desfazer?
	public BigDecimal getPrice(TypeOfPayment typeOfPayment) {
		switch (typeOfPayment) {
		case HORA:
			return this.hourPrice;

		case DIA:
			return this.dayPrice;

		case MES:
			return this.monthPrice;

		default:
			throw null;
		}
	}
}
