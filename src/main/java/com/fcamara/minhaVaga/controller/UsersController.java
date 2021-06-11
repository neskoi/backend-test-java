package com.fcamara.minhaVaga.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fcamara.minhaVaga.dto.request.UserDtoRequest;
import com.fcamara.minhaVaga.dto.request.UserDtoUpdateEmailRequest;
import com.fcamara.minhaVaga.dto.request.UserDtoUpdatePasswordRequest;
import com.fcamara.minhaVaga.dto.request.VehicleDtoRequest;
import com.fcamara.minhaVaga.dto.response.UserDtoResponse;
import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.model.Vehicle;
import com.fcamara.minhaVaga.service.UserService;
import com.fcamara.minhaVaga.service.VehicleService;

@RestController
@RequestMapping("/user")
public class UsersController {

	@Autowired
	private UserService userService;

	@Autowired
	private VehicleService vehicleService;

	@GetMapping("/{id}")
	public ResponseEntity<UserDtoResponse> findOneUser(@PathVariable Long id) {
		User user = userService.findOneUser(id);
		return ResponseEntity.ok(new UserDtoResponse(user));
	};

	//Acredito que essa rota seja desnecessaria.
	@GetMapping("/vehicle/{id}")
	public ResponseEntity<Vehicle> findOneVehicle(@PathVariable Long id){
		Vehicle vehicle = vehicleService.findOneVehicle(id);
		return ResponseEntity.ok(vehicle);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDtoResponse> register(@Valid @RequestBody UserDtoRequest userRequest,
			UriComponentsBuilder uriBuilder) {
		User userToRegister = userRequest.convertToUser();
		User registeredUser = userService.register(userToRegister);
		URI uri = uriBuilder.path("/user/{id}").buildAndExpand(registeredUser.getId()).toUri();// Realmente necessario?
		return ResponseEntity.created(uri).body(new UserDtoResponse(registeredUser));
	}

	@PostMapping("{userId}/register-vehicle")
	public ResponseEntity<Vehicle> registerVehicle(@PathVariable Long userId,
			@Valid @RequestBody VehicleDtoRequest vehicleRequest, UriComponentsBuilder uriBuilder) {
		Vehicle registeredVehicle = vehicleService.registerVehicle(userId, vehicleRequest);
		URI uri = uriBuilder.path("user/vehicle/{id}").buildAndExpand(registeredVehicle.getId()).toUri();
		return ResponseEntity.created(uri).body(registeredVehicle);
	}

	@PutMapping("/update-email/{id}")
	public ResponseEntity<UserDtoResponse> updateEmail(@PathVariable Long id,
			@Valid @RequestBody UserDtoUpdateEmailRequest email) {
		User response = userService.updateEmail(id, email);
		return ResponseEntity.ok(new UserDtoResponse(response));
	}

	@PutMapping("/update-password/{id}")
	public ResponseEntity<UserDtoResponse> updatePassword(@PathVariable Long id,
			@Valid @RequestBody UserDtoUpdatePasswordRequest password) {
		User response = userService.updatePassword(id, password);
		return ResponseEntity.ok(new UserDtoResponse(response));
	}

	public ResponseEntity<?> updateVehicleColor(){
		return null;
	}
}
