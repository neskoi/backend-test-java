package com.fcamara.minhaVaga.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcamara.minhaVaga.config.security.TokenService;
import com.fcamara.minhaVaga.dto.request.CarParkUsageDtoRequest;
import com.fcamara.minhaVaga.dto.response.CarParkUsageDtoResponse;
import com.fcamara.minhaVaga.model.CarParkUsage;
import com.fcamara.minhaVaga.service.CarParkUsageService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/user/on/{vacancyId}/vehicle/{vehicleId}")
public class CarParkUsageController {

	@Autowired
	CarParkUsageService carParkUsageService;
	
	@Autowired
	TokenService tokenService;

	@PostMapping("/park")
	@Operation(summary = "Registra a entrade de um carro em um estacionamento")
	public ResponseEntity<CarParkUsage> parking(HttpServletRequest request, @PathVariable Long vacancyId, @PathVariable Long vehicleId,
			@Valid @RequestBody CarParkUsageDtoRequest typeOfPayment) {		
		Long carParkId = tokenService.returnRequesterId(request);
		CarParkUsage carParkUsage = carParkUsageService.insertParking(carParkId, vacancyId, vehicleId,
				typeOfPayment.getTypeOfPayment());
		return ResponseEntity.ok(carParkUsage);
	}

	@PostMapping("/leave")
	@Operation(summary = "Registra a saida de um carro de um estacionamento")
	public ResponseEntity<CarParkUsageDtoResponse> leave(HttpServletRequest request, @PathVariable Long vehicleId) {
		Long carParkId = tokenService.returnRequesterId(request);
		CarParkUsage carParkUsage = carParkUsageService.leaveParking(carParkId, vehicleId);
		return ResponseEntity.ok(new CarParkUsageDtoResponse(carParkUsage));
	}

}
