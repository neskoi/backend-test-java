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
import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	private PasswordEncoder bcrypt;
	
	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder bcrypt) {
		super();
		this.userRepository = userRepository;
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
		return insertUser(user);
	}

	@Transactional
	public User updateEmail(Long id, UserDtoUpdateEmailRequest email) {
		Optional<User> checkUser = userRepository.findById(id);
		if (checkUser.isPresent()) {
			User user = checkUser.get();
			if (isEmailAlreadyRegistered(email.getEmail()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email indisponivel");
			user.setEmail(email.getEmail());
			return user;
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	@Transactional
	public User updatePassword(Long id, UserDtoUpdatePasswordRequest password) {
		Optional<User> checkUser = userRepository.findById(id);
		if (checkUser.isPresent()) {
			User user = checkUser.get();
			user.setPassword(password.getPassword());
			encodePassword(user);
			return user;
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	private void encodePassword(User user) {
		user.setPassword(bcrypt.encode(user.getPassword()));
	}

	private boolean isEmailAlreadyRegistered(String email) {
		try {
			Optional<User> emailRegistered = userRepository.findByEmail(email);
			if (emailRegistered.isPresent())
				return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Um erro inexperado ocorreu, tente novamente mais tarde.");
		}
		return false;
	}

	private boolean isCpfAlreadyRegistered(String cpf) {
		try {
			Optional<User> cpfRegistered = userRepository.findByCpf(cpf);
			if (cpfRegistered != null)
				return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Um erro inexperado ocorreu, tente novamente mais tarde.");
		}
		return false;
	}

	private User insertUser(User user) {
		try {
			userRepository.save(user);
			return user;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Um erro inexperado ocorreu, tente novamente mais tarde.");
		}
	}

}
