package com.fcamara.minhaVaga.controller;

import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fcamara.minhaVaga.config.security.TokenService;
import com.fcamara.minhaVaga.dto.response.ReportDtoResponse;
import com.fcamara.minhaVaga.service.CarParkUsageService;
import com.fcamara.minhaVaga.util.TimeSpaces;

@RestController
@RequestMapping("/carpark/report")
public class ReportController {
	@Autowired
	CarParkUsageService carParkUsageService;

	@Autowired
	TokenService tokenService;

	@GetMapping("/last-interval")
	public ResponseEntity<?> reportLastChoosedInterval(HttpServletRequest request,
			@RequestParam TimeSpaces interval) {
		Long carParkId = tokenService.returnRequesterId(request);
		ReportDtoResponse report = carParkUsageService.reportLastChoosedInterval(carParkId, interval);
		return ResponseEntity.ok(report);
	}

	@GetMapping("/custom-interval")
	public ResponseEntity<?> reportCustomInterval(HttpServletRequest request,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime entranceTime,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime exitTime) {
		Long carParkId = tokenService.returnRequesterId(request);
		ReportDtoResponse report = carParkUsageService.reportCustomInterval(carParkId, entranceTime, exitTime);
		return ResponseEntity.ok(report);
	}
}
