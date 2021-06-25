package com.fcamara.minhaVaga.service;

import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fcamara.minhaVaga.dto.request.UserDtoUpdateEmailRequest;
import com.fcamara.minhaVaga.dto.request.UserDtoUpdatePasswordRequest;
import com.fcamara.minhaVaga.exception.UserAlreadyExistsException;
import com.fcamara.minhaVaga.model.Role;
import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.repository.RoleRepository;
import com.fcamara.minhaVaga.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	
	private RoleRepository roleRepository;

	private PasswordEncoder bcrypt;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder bcrypt) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bcrypt = bcrypt;
	}

	public User findOneUser(Long id) {
		Optional<User> searchedUser = userRepository.findById(id);
		if (searchedUser.isPresent())
			return searchedUser.get();
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	public User register(User user) {
		if (isCpfAlreadyRegistered(user.getCpf()) || isEmailAlreadyRegistered(user.getEmail()))
			throw new UserAlreadyExistsException("Usuario já cadastrado.");
		encodePassword(user);
		user.getRoles().add(findRoleByName("CAROWNER"));
		return insertUser(user);
	}

	@Transactional
	public User updateEmail(Long id, UserDtoUpdateEmailRequest email) {
		User user = findOneUser(id);
		if (isEmailAlreadyRegistered(email.getEmail()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email indisponivel");
		user.setEmail(email.getEmail());
		return user;
	}

	@Transactional
	public User updatePassword(Long id, UserDtoUpdatePasswordRequest password) {
		User user = findOneUser(id);
		user.setPassword(password.getPassword());
		encodePassword(user);
		return user;
	}

	private void encodePassword(User user) {
		user.setPassword(bcrypt.encode(user.getPassword()));
	}

	private Role findRoleByName(String name) {
		Optional<Role> role = roleRepository.findByName(name);
		if(role.isPresent())
			return role.get();
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalida");		
	} 
	
	private boolean isEmailAlreadyRegistered(String email) {
		Optional<User> emailRegistered = userRepository.findByEmail(email);
		if (emailRegistered.isPresent())
			return true;
		return false;
	}

	private boolean isCpfAlreadyRegistered(String cpf) {
		Optional<User> cpfRegistered = userRepository.findByCpf(cpf);
		if (cpfRegistered != null)
			return true;
		return false;
	}

	private User insertUser(User user) {
		userRepository.save(user);
		return user;
	}

}
