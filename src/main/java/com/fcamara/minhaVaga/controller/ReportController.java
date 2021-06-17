package com.fcamara.minhaVaga.controller;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fcamara.minhaVaga.dto.response.ReportDtoResponse;
import com.fcamara.minhaVaga.service.CarParkUsageService;
import com.fcamara.minhaVaga.util.TimeSpaces;

@RestController
@RequestMapping("/carpark/{carParkId}/report")
public class ReportController {
	@Autowired
	CarParkUsageService carParkUsageService;

	@GetMapping("/last-interval-report")
	public ResponseEntity<?> reportLastChoosedInterval(@PathVariable Long carParkId,
			@RequestParam TimeSpaces reportCreator) {
		ReportDtoResponse report = carParkUsageService.reportLastChoosedInterval(carParkId, reportCreator);
		return ResponseEntity.ok(report);
	}

	@GetMapping("/custom-interval-report")
	public ResponseEntity<?> reportCustomInterval(@PathVariable Long carParkId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime entranceTime,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime exitTime) {

		ReportDtoResponse report = carParkUsageService.reportCustomInterval(carParkId, entranceTime, exitTime);
		return ResponseEntity.ok(report);
	}
}
