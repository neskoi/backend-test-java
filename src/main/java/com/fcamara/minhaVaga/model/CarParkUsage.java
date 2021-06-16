package com.fcamara.minhaVaga.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
	private ZonedDateTime entranceTime;

	private ZonedDateTime exitTime;

	@Enumerated(EnumType.STRING)
	private TypeOfPayment typeOfPayment;

	private BigDecimal basePaidPrice;

	private BigDecimal totalPrice;

	public CarParkUsage(Vacancy vacancy, Vehicle vehicle, TypeOfPayment typeOfPayment) {
		this.entranceTime = ZonedDateTime.now();
		this.vacancy = vacancy;
		this.vehicle = vehicle;
		this.typeOfPayment = typeOfPayment;
		this.basePaidPrice = vacancy.getPrice(typeOfPayment);
	}

	public void exit() {
		this.exitTime = ZonedDateTime.now();
		this.totalPrice = this.calculateTotalPrice();
	}
	
	public BigDecimal calculateTotalPrice() {
		long elapsedSeconds = ChronoUnit.SECONDS.between(entranceTime, exitTime);
		switch (this.typeOfPayment) {
		case HORA:
			double fractionalHours = elapsedSeconds / 3600.0;
			return this.basePaidPrice.multiply(new BigDecimal(fractionalHours)).setScale(2, RoundingMode.DOWN);
		case DIA:
			return this.basePaidPrice;
		case MES:
			return new BigDecimal(0);
		default:
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Metrica temporal invalida.");
		}
	}

}
