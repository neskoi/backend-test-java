package com.fcamara.minhaVaga.dto.response;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Page;

import com.fcamara.minhaVaga.model.CarParkUsage;
import com.fcamara.minhaVaga.model.TypeOfPayment;
import com.fcamara.minhaVaga.model.Vacancy;
import com.fcamara.minhaVaga.model.Vehicle;

import lombok.Getter;

@Getter
public class CarParkUsageDtoResponse {

	private Vehicle vehicle;

	private Vacancy vacancy;

	private ZonedDateTime entranceTime;

	private ZonedDateTime exitTime;

	private TypeOfPayment typeOfPayment;

	private BigDecimal basePaidPrice;

	private String parkedTime;
	
	private BigDecimal totalPrice;
	

	public CarParkUsageDtoResponse(CarParkUsage carParkUsage) {
		this.vehicle = carParkUsage.getVehicle();
		this.vacancy = carParkUsage.getVacancy();
		this.entranceTime = carParkUsage.getEntranceTime();
		this.exitTime = carParkUsage.getExitTime();
		this.typeOfPayment = carParkUsage.getTypeOfPayment();
		this.basePaidPrice = carParkUsage.getBasePaidPrice();
		this.totalPrice = carParkUsage.getTotalPrice();
		this.parkedTime = elapsedTime();
	}

	public static Page<CarParkUsageDtoResponse> bulkConvertToDtoResponse(Page<CarParkUsage> carParkUsages){
		return carParkUsages.map(CarParkUsageDtoResponse::new);
	}
	
	public String elapsedTime() {
		long elapsedSeconds = ChronoUnit.SECONDS.between(entranceTime, exitTime);
		long hours = elapsedSeconds / 3600;
		long minutes = (elapsedSeconds % 3600) / 60;
		long seconds = elapsedSeconds % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}
