package com.fcamara.minhaVaga.dto.response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fcamara.minhaVaga.model.CarParkUsage;
import com.fcamara.minhaVaga.model.TypeOfPayment;
import com.fcamara.minhaVaga.model.Vacancy;
import com.fcamara.minhaVaga.model.Vehicle;

import lombok.Getter;

@Getter
public class CarParkUsageDtoResponse {

	private Vehicle vehicle;

	private Vacancy vacancy;

	private ZonedDateTime entraceTime;

	private ZonedDateTime exitTime;

	private TypeOfPayment typeOfPayment;

	private BigDecimal basePaidPrice;

	private String parkedTime;
	
	private BigDecimal totalPrice;
	

	public CarParkUsageDtoResponse(CarParkUsage carParkUsage) {
		this.vehicle = carParkUsage.getVehicle();
		this.vacancy = carParkUsage.getVacancy();
		this.entraceTime = carParkUsage.getEntraceTime();
		this.exitTime = carParkUsage.getExitTime();
		this.typeOfPayment = carParkUsage.getTypeOfPayment();
		this.basePaidPrice = carParkUsage.getBasePaidPrice();
		long elapsedSeconds = ChronoUnit.SECONDS.between(entraceTime, exitTime);
		this.totalPrice = calculateTotalPrice(elapsedSeconds);
		this.parkedTime = elapsedTime(elapsedSeconds);
	}

	private BigDecimal calculateTotalPrice(long elapsedSeconds) {
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
	
	private String elapsedTime(long elapsedSeconds) {
		long hours = elapsedSeconds / 3600;
		long minutes = (elapsedSeconds % 3600) / 60;
		long seconds = elapsedSeconds % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}
