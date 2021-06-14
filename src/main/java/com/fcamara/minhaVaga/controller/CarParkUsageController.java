package com.fcamara.minhaVaga.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcamara.minhaVaga.dto.request.CarParkUsageDtoRequest;
import com.fcamara.minhaVaga.dto.response.CarParkUsageDtoResponse;
import com.fcamara.minhaVaga.model.CarParkUsage;
import com.fcamara.minhaVaga.service.CarParkUsageService;

@RestController
@RequestMapping("/carpark-vacancy/{vacancyId}/vehicle/{vehicleId}")
public class CarParkUsageController {
	
	@Autowired
	CarParkUsageService carParkUsageService;

	@PostMapping("/parking")
	public ResponseEntity<CarParkUsage> parking(@PathVariable Long vacancyId, @PathVariable Long vehicleId, @Valid @RequestBody CarParkUsageDtoRequest typeOfPayment) {
		CarParkUsage carParkUsage = carParkUsageService.insertParking(vacancyId, vehicleId, typeOfPayment.getTypeOfPayment());
		return ResponseEntity.ok(carParkUsage);
	}
	
	@PostMapping("/leave")
	public ResponseEntity<CarParkUsageDtoResponse> leave(@PathVariable Long vehicleId) {
		CarParkUsage carParkUsage = carParkUsageService.leaveParking(vehicleId);
		return ResponseEntity.ok(new CarParkUsageDtoResponse(carParkUsage));
	}  
}
