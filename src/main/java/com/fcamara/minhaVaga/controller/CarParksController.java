package com.fcamara.minhaVaga.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fcamara.minhaVaga.config.security.TokenService;
import com.fcamara.minhaVaga.dto.request.CarParkAdressDtoRequest;
import com.fcamara.minhaVaga.dto.request.CarParkDtoEmailRequest;
import com.fcamara.minhaVaga.dto.request.CarParkDtoPasswordRequest;
import com.fcamara.minhaVaga.dto.request.CarParkDtoPhoneRequest;
import com.fcamara.minhaVaga.dto.request.CarParkDtoRequest;
import com.fcamara.minhaVaga.dto.request.VacancyDtoRequest;
import com.fcamara.minhaVaga.dto.response.CarParkDtoResponse;
import com.fcamara.minhaVaga.model.Adress;
import com.fcamara.minhaVaga.model.CarPark;
import com.fcamara.minhaVaga.model.Vacancy;
import com.fcamara.minhaVaga.service.CarParkService;

@RestController
@RequestMapping("/carpark")
public class CarParksController {

	@Autowired
	CarParkService carParkService;
	
	@Autowired
	TokenService tokenService;

	@GetMapping("/{id}")
	public ResponseEntity<CarParkDtoResponse> listOneCarParks(@PathVariable Long id) {
		CarPark carPark = carParkService.findOneCarPark(id);
		return ResponseEntity.ok(new CarParkDtoResponse(carPark));
	}

	@PostMapping("/register")
	public ResponseEntity<CarParkDtoResponse> registerCarPark(@Valid @RequestBody CarParkDtoRequest carParkRequest,
			UriComponentsBuilder uriBuilder) {
		CarPark carParkToRegister = carParkRequest.convertToCarPark();
		CarPark registeredCarPark = carParkService.register(carParkToRegister);
		URI uri = uriBuilder.path("/carpark/{id}").buildAndExpand(registeredCarPark.getId()).toUri();
		return ResponseEntity.created(uri).body(new CarParkDtoResponse(registeredCarPark));
	};

	@PostMapping("/register-adress")
	public ResponseEntity<CarParkDtoResponse> registerParkAdress(HttpServletRequest request,
			@Valid @RequestBody CarParkAdressDtoRequest carParkAdressRequest, UriComponentsBuilder uriBuilder) {
		Long id = tokenService.returnRequesterId(request);
		CarPark carPark = carParkService.registerAdress(id, carParkAdressRequest);
		URI uri = uriBuilder.path("/carpark/{id}").buildAndExpand(carPark.getId()).toUri();
		return ResponseEntity.created(uri).body(new CarParkDtoResponse(carPark));
	};

	@PostMapping("/adress/{adressId}/register-vacancy")
	public ResponseEntity<Adress> registerAdressVacancy(HttpServletRequest request, @PathVariable Long adressId,
			@Valid @RequestBody VacancyDtoRequest vacancyRequest, UriComponentsBuilder uriBuilder) {
		Long carParkId = tokenService.returnRequesterId(request);
		Adress adress = carParkService.registerAdressVacancy(carParkId, adressId, vacancyRequest);
		URI uri = uriBuilder.path("/carpark/{id}").buildAndExpand(adress.getId()).toUri();
		return ResponseEntity.created(uri).body(adress);
	}

	@PutMapping("/adress/update-vacancy/{vacancyId}")
	public ResponseEntity<Vacancy> updateAdressVacancy(HttpServletRequest request, @PathVariable Long vacancyId,
			@Valid @RequestBody VacancyDtoRequest vacancyRequest) {
		Long carParkId = tokenService.returnRequesterId(request);
		Vacancy modifiedVacancy = carParkService.updateAdressVacancy(carParkId, vacancyId, vacancyRequest);
		return ResponseEntity.ok(modifiedVacancy);
	}

	@PutMapping("/update-email")
	public ResponseEntity<CarParkDtoResponse> updateEmail(HttpServletRequest request,
			@Valid @RequestBody CarParkDtoEmailRequest carParkRequest) {
		Long id = tokenService.returnRequesterId(request);
		CarPark response = carParkService.updateEmail(id, carParkRequest);
		return ResponseEntity.ok(new CarParkDtoResponse(response));
	};

	@PutMapping("/update-password")
	public ResponseEntity<CarParkDtoResponse> updatePassword(HttpServletRequest request,
			@Valid @RequestBody CarParkDtoPasswordRequest carParkRequest) {
		Long id = tokenService.returnRequesterId(request);
		CarPark response = carParkService.updatePassword(id, carParkRequest);
		return ResponseEntity.ok(new CarParkDtoResponse(response));
	};

	@PutMapping("/update-phone")
	public ResponseEntity<CarParkDtoResponse> updatePhone(HttpServletRequest request,
			@Valid @RequestBody CarParkDtoPhoneRequest carParkRequest) {
		Long id = tokenService.returnRequesterId(request);
		CarPark response = carParkService.updatePhone(id, carParkRequest);
		return ResponseEntity.ok(new CarParkDtoResponse(response));
	};

	@DeleteMapping("/adress/{adressId}/delete")
	public ResponseEntity<?> deleteAdress(HttpServletRequest request, @PathVariable Long adressId) {
		Long carParkId = tokenService.returnRequesterId(request);
		carParkService.deleteAdress(carParkId, adressId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/adress/vacancy/{vacancyId}/delete")
	public ResponseEntity<?> deleteAdressVacancy(HttpServletRequest request, @PathVariable Long vacancyId) {
		Long carParkId = tokenService.returnRequesterId(request);
		carParkService.deleteAdressVacancy(carParkId, vacancyId);
		return ResponseEntity.ok().build();
	}

}
