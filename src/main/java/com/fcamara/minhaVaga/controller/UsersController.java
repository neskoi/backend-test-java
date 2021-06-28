package com.fcamara.minhaVaga.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.fcamara.minhaVaga.dto.request.UserDtoRequest;
import com.fcamara.minhaVaga.dto.request.UserDtoUpdateEmailRequest;
import com.fcamara.minhaVaga.dto.request.UserDtoUpdatePasswordRequest;
import com.fcamara.minhaVaga.dto.request.VehicleDtoRequest;
import com.fcamara.minhaVaga.dto.response.UserDtoResponse;
import com.fcamara.minhaVaga.model.Color;
import com.fcamara.minhaVaga.model.Model;
import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.model.Vehicle;
import com.fcamara.minhaVaga.service.UserService;
import com.fcamara.minhaVaga.service.VehicleService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/user")
public class UsersController {

	@Autowired
	private UserService userService;

	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private TokenService tokenService;

	@GetMapping("/info")
	@Operation(summary = "Retorna informação do usuário autenticado")
	public ResponseEntity<UserDtoResponse> findOneUser(HttpServletRequest request) {
		Long userId = tokenService.returnRequesterId(request);
		User user = userService.findOneUser(userId);
		return ResponseEntity.ok(new UserDtoResponse(user));
	};
	
	@GetMapping("/vehicle/all/colors")
	@Operation(summary = "Lista todos os cores possivei para veiculos")
	public ResponseEntity<Page<Color>> listAllColors(@PageableDefault(page = 0, size = 50) Pageable pageable){
		return ResponseEntity.ok(vehicleService.listAllColors(pageable));
	}
	
	@GetMapping("/vehicle/all/models")
	@Operation(summary = "Lista todos os modelos possivei para veiculos")
	public ResponseEntity<Page<Model>> listAllModels(@PageableDefault(page = 0, size = 50) Pageable pageable){
		return ResponseEntity.ok(vehicleService.listAllModels(pageable));
	}
	
	@PostMapping("/register")
	@Operation(summary = "Registra um usuário")
	public ResponseEntity<UserDtoResponse> register(@Valid @RequestBody UserDtoRequest userRequest,
			UriComponentsBuilder uriBuilder) {
		User userToRegister = userRequest.convertToUser();
		User registeredUser = userService.register(userToRegister);
		URI uri = uriBuilder.path("/user/{id}").buildAndExpand(registeredUser.getId()).toUri();// Realmente necessario?
		return ResponseEntity.created(uri).body(new UserDtoResponse(registeredUser));
	}

	@PostMapping("/register-vehicle")
	@Operation(summary = "Registra um veiculo do usuário autenticado")
	public ResponseEntity<Vehicle> registerVehicle(HttpServletRequest request,
			@Valid @RequestBody VehicleDtoRequest vehicleRequest, UriComponentsBuilder uriBuilder) {
		Long userId = tokenService.returnRequesterId(request);
		Vehicle registeredVehicle = vehicleService.registerVehicle(userId, vehicleRequest);
		URI uri = uriBuilder.path("user/vehicle/{id}").buildAndExpand(registeredVehicle.getId()).toUri();
		return ResponseEntity.created(uri).body(registeredVehicle);
	}

	@PutMapping("/update-email")
	@Operation(summary = "Atualiza o e-mail do usuário autenticado")
	public ResponseEntity<UserDtoResponse> updateEmail(HttpServletRequest request,
			@Valid @RequestBody UserDtoUpdateEmailRequest email) {
		Long userId = tokenService.returnRequesterId(request);
		User response = userService.updateEmail(userId, email);
		return ResponseEntity.ok(new UserDtoResponse(response));
	}

	@PutMapping("/update-password")
	@Operation(summary = "Atualiza a senha do usuário autenticado")
	public ResponseEntity<UserDtoResponse> updatePassword(HttpServletRequest request,
			@Valid @RequestBody UserDtoUpdatePasswordRequest password) {
		Long userId = tokenService.returnRequesterId(request);
		User response = userService.updatePassword(userId, password);
		return ResponseEntity.ok(new UserDtoResponse(response));
	}

	@PutMapping("/vehicle/{vehicleId}/change-color/{colorId}")
	@Operation(summary = "Atualiza cor de um veiculo do usuário autenticado")
	public ResponseEntity<Vehicle> updateVehicleColor(HttpServletRequest request, @PathVariable Long vehicleId, @PathVariable Long colorId){
		Long userId = tokenService.returnRequesterId(request);
		Vehicle vehicle = vehicleService.changeVehicleColor(userId, vehicleId, colorId);
		return ResponseEntity.ok(vehicle);
	}
	
	@DeleteMapping("vehicle/{vehicleId}/delete")
	@Operation(summary = "Apaga um veiculo do usuário autenticado")
	public ResponseEntity<?> deleteVehicle(HttpServletRequest request, @PathVariable Long vehicleId){
		Long userId = tokenService.returnRequesterId(request);
		vehicleService.deleteVehicle(vehicleId, userId);
		return ResponseEntity.ok().build();
	}
}
