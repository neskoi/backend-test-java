package com.fcamara.minhaVaga.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcamara.minhaVaga.config.security.TokenService;
import com.fcamara.minhaVaga.dto.request.LoginFormDto;
import com.fcamara.minhaVaga.dto.response.TokenDtoResponse;

@RestController
@RequestMapping
public class AuthController {
	
	@Autowired @Qualifier("UserAuthenticationManager")
	private AuthenticationManager authUserManager;

	@Autowired @Qualifier("CarParkAuthenticationManager")
	private AuthenticationManager authCarParkManager;
	
	@Autowired
	private TokenService tokenService;

	//AuthenticationException
	@PostMapping("/user/auth")
	public ResponseEntity<TokenDtoResponse> userLogin(@Valid @RequestBody LoginFormDto loginForm) {
		try {
			Authentication auth = authUserManager.authenticate(loginForm.convertToAuthToken());
			String token = tokenService.generateUserToken(auth);
			return ResponseEntity.ok(new TokenDtoResponse(token, "Bearer"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/carpark/auth")
	public ResponseEntity<?> carParkLogin(@Valid @RequestBody LoginFormDto loginForm) {
		try {
			Authentication auth = authCarParkManager.authenticate(loginForm.convertToAuthToken());	
			String token = tokenService.generateCarParkToken(auth);	
			return ResponseEntity.ok(new TokenDtoResponse(token, "Bearer"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
