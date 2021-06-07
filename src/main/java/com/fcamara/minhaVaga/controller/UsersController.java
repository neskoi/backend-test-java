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
import com.fcamara.minhaVaga.dto.request.UserDtoUpdateRequest;
import com.fcamara.minhaVaga.dto.response.UserDtoResponse;
import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.service.UserService;

@RestController
@RequestMapping("/user")
public class UsersController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public UserDtoResponse findOneUser(@PathVariable Long id) {
		User user = userService.findOneUser(id);
		return new UserDtoResponse(user);
	};

	@PostMapping("/register")
	public ResponseEntity<UserDtoResponse> register(@Valid @RequestBody UserDtoRequest userRequest,
			UriComponentsBuilder uriBuilder) {
		User userToRegister = userRequest.convertToUser();
		User registeredUser = userService.register(userToRegister);
		URI uri = uriBuilder.path("/user/{id}").buildAndExpand(registeredUser.getId()).toUri();// Realmente necessario?
		return ResponseEntity.created(uri).body(new UserDtoResponse(registeredUser));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<UserDtoResponse> updateEmailOrPassword(@PathVariable Long id,
			@Valid @RequestBody UserDtoUpdateRequest userRequest) {
		User response = userService.updateEmailOrPassword(id, userRequest);
		return ResponseEntity.ok(new UserDtoResponse(response));
	}
}
