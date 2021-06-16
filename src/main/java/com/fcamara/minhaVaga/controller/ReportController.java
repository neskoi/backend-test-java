package com.fcamara.minhaVaga.controller;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fcamara.minhaVaga.dto.response.CarParkUsageDtoResponse;
import com.fcamara.minhaVaga.service.CarParkUsageService;

@RestController
@RequestMapping("/carpark/{carParkId}/report")
public class ReportController {
	@Autowired
	CarParkUsageService carParkUsageService;

	@GetMapping("/between-dates")
	public ResponseEntity<Page<CarParkUsageDtoResponse>> searchRegistersBetweenDates( @PathVariable Long carParkId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime entranceTime,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime exitTime,
			@RequestParam int page, @RequestParam int amount) {
		Page<CarParkUsageDtoResponse> searchedUsages = carParkUsageService.searchRegistersBetweenDates(carParkId, entranceTime,
				exitTime, page, amount);
		return ResponseEntity.ok(searchedUsages);
	}
	
	@GetMapping("/after-date")
	public ResponseEntity<Page<CarParkUsageDtoResponse>> searchRegistersAfterDates( @PathVariable Long carParkId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime entranceTime,
			@RequestParam int page, @RequestParam int amount) {
		Page<CarParkUsageDtoResponse> searchedUsages = carParkUsageService.searchRegistersAfterDate(carParkId, entranceTime, page, amount);
		return ResponseEntity.ok(searchedUsages);
	}
	
	@GetMapping("/last-hour")
	public ResponseEntity<?> lastHourResume(){
			
		return null;
	}
}
