package com.fcamara.minhaVaga.controller;

import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fcamara.minhaVaga.config.security.TokenService;
import com.fcamara.minhaVaga.dto.response.CarParkUsageDtoResponse;
import com.fcamara.minhaVaga.service.CarParkUsageService;
import com.fcamara.minhaVaga.util.TimeSpaces;

@RestController
@RequestMapping("/carpark/usage-info")
public class CarParkUsageListsController {

	@Autowired
	CarParkUsageService carParkUsageService;

	@Autowired
	TokenService tokenService;
	
	@GetMapping("/between-choosed-dates")
	public ResponseEntity<Page<CarParkUsageDtoResponse>> listWhoEnteredInLastCustomInterval(HttpServletRequest request,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime entranceTime,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime exitTime,
			@RequestParam(required = false) boolean leaves, @PageableDefault(page = 0, size = 50) Pageable pageable) {
		Long carParkId = tokenService.returnRequesterId(request);
		Page<CarParkUsageDtoResponse> searchedUsages = carParkUsageService.listWhoEnteredInLastCustomInterval(carParkId,
				entranceTime, exitTime, leaves, pageable);
		return ResponseEntity.ok(searchedUsages);
	}

	@GetMapping("/between-fixed-intervals")
	public ResponseEntity<Page<CarParkUsageDtoResponse>> listWhoEnteredInLastChoosedInterval(HttpServletRequest request,
			@RequestParam TimeSpaces interval, @RequestParam(required = false) boolean leaves,
			@PageableDefault(page = 0, size = 50) Pageable pageable) {
		Long carParkId = tokenService.returnRequesterId(request);
		Page<CarParkUsageDtoResponse> searchedUsages = carParkUsageService
				.listWhoEnteredInLastChoosedInterval(carParkId, interval, leaves, pageable);
		return ResponseEntity.ok(searchedUsages);
	}

}
