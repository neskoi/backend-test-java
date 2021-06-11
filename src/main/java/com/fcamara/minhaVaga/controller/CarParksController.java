package com.fcamara.minhaVaga.controller;

import java.net.URI;
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

	@PostMapping("/{id}/register-adress")
	public ResponseEntity<CarParkDtoResponse> registerParkAdress(@PathVariable Long id,
			@Valid @RequestBody CarParkAdressDtoRequest carParkAdressRequest, UriComponentsBuilder uriBuilder) {
		CarPark carPark = carParkService.registerAdress(id, carParkAdressRequest);
		URI uri = uriBuilder.path("/carpark/{id}").buildAndExpand(carPark.getId()).toUri();
		return ResponseEntity.created(uri).body(new CarParkDtoResponse(carPark));
	};

	@PostMapping("/adress/{idAdress}/register-vacancy")
	public ResponseEntity<Adress> registerAdressVacancy(@PathVariable Long idAdress,
			@Valid @RequestBody VacancyDtoRequest vacancyRequest, UriComponentsBuilder uriBuilder) {
		Adress adress = carParkService.registerAdressVacancy(idAdress, vacancyRequest);
		URI uri = uriBuilder.path("/carpark/{id}").buildAndExpand(adress.getId()).toUri();
		return ResponseEntity.created(uri).body(adress);
	}

	@PutMapping("/adress/update-vacancy/{idVacancy}")
	public ResponseEntity<Vacancy> updateAdressVacancy(@PathVariable Long idVacancy,
			@Valid @RequestBody VacancyDtoRequest vacancyRequest) {
		Vacancy modifiedVacancy = carParkService.updateAdressVacancy(idVacancy, vacancyRequest);
		return ResponseEntity.ok(modifiedVacancy);
	}

	@PutMapping("/update-email/{id}")
	public ResponseEntity<CarParkDtoResponse> updateEmail(@PathVariable Long id,
			@Valid @RequestBody CarParkDtoEmailRequest carParkRequest) {
		CarPark response = carParkService.updateEmail(id, carParkRequest);
		return ResponseEntity.ok(new CarParkDtoResponse(response));
	};

	@PutMapping("/update-password/{id}")
	public ResponseEntity<CarParkDtoResponse> updatePassword(@PathVariable Long id,
			@Valid @RequestBody CarParkDtoPasswordRequest carParkRequest) {
		CarPark response = carParkService.updatePassword(id, carParkRequest);
		return ResponseEntity.ok(new CarParkDtoResponse(response));
	};

	@PutMapping("/update-phone/{id}")
	public ResponseEntity<CarParkDtoResponse> updatePhone(@PathVariable Long id,
			@Valid @RequestBody CarParkDtoPhoneRequest carParkRequest) {
		CarPark response = carParkService.updatePhone(id, carParkRequest);
		return ResponseEntity.ok(new CarParkDtoResponse(response));
	};

	@DeleteMapping("/adress/{id}/delete")
	public ResponseEntity<?> deleteAdress(@PathVariable Long id) {
		carParkService.deleteAdress(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/adress/vacancy/{id}/delete")
	public ResponseEntity<?> deleteAdressVacancy(@PathVariable Long id) {
		carParkService.deleteAdressVacancy(id);
		return ResponseEntity.ok().build();
	}

}
