package com.fcamara.minhaVaga.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fcamara.minhaVaga.dto.request.UserDtoUpdateRequest;
import com.fcamara.minhaVaga.exception.UserAlreadyExistsException;
import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.repository.UsersRepository;

@Service
public class UserService {

	@Autowired
	UsersRepository userRepository;

	@Autowired
	PasswordEncoder bcrypt;

	public User findOneUser(Long id) {
		Optional<User> searchedUser = userRepository.findById(id);
		if (searchedUser.isPresent())
			return searchedUser.get();
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	public User register(User user) {
		toOnlyNumberCPF(user);
		if (isCpfOrEmailAlreadyRegistered(user))
			throw new UserAlreadyExistsException("Usuario já cadastrado.");
		encodePassword(user);
		return insertUser(user);
	}

	@Transactional
	public User updateEmailOrPassword(Long id, UserDtoUpdateRequest userRequest) {
		Optional<User> checkUser = userRepository.findById(id);
		if (checkUser.isPresent())
			return userRequest.update(checkUser.get());
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	private void encodePassword(User user) {
		user.setPassword(bcrypt.encode(user.getPassword()));
	}

	private void toOnlyNumberCPF(User user) {
		user.setCpf(user.getCpf().replaceAll("\\D", ""));
	}

	private boolean isCpfOrEmailAlreadyRegistered(User user) {
		try {
			User cpfRegistered = userRepository.findByCpf(user.getCpf());
			User emailRegistered = userRepository.findByEmail(user.getEmail());
			if (cpfRegistered != null || emailRegistered != null)
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
