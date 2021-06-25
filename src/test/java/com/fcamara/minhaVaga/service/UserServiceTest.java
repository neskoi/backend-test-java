package com.fcamara.minhaVaga.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.fcamara.minhaVaga.dto.request.UserDtoUpdateEmailRequest;
import com.fcamara.minhaVaga.dto.request.UserDtoUpdatePasswordRequest;
import com.fcamara.minhaVaga.exception.UserAlreadyExistsException;
import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.repository.UserRepository;

public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder bcrypt;

	private UserService userService;

	@BeforeEach
	public void BeforeEach() {
		MockitoAnnotations.openMocks(this);
		this.bcrypt = new BCryptPasswordEncoder();
		this.userService = new UserService(userRepository, bcrypt);
	}

	@Test
	public void shouldReturnOneUserById() {
		Long searchedUser = 1l;
		Mockito.when(userRepository.findById(anyLong())).thenReturn(repoUserFindOneUserMockBehavior(searchedUser));
		User user = userService.findOneUser(searchedUser);
		Assertions.assertTrue(user instanceof User);
	}

	@Test
	public void shouldThrowsExceptionIfInvalidUser() {
		Long searchedUser = 10l;
		Mockito.when(userRepository.findById(anyLong())).thenReturn(repoUserFindOneUserMockBehavior(searchedUser));
		Assertions.assertThrows(ResponseStatusException.class, () -> userService.findOneUser(searchedUser));
	}

	@Test
	public void shouldThrowsExceptionIfCpfIsAlreadyInUse() {
		User user = new User("Robissu", "123456789", "07450740060", "robissu@com.com");
		Mockito.when(userRepository.findByCpf(anyString())).thenReturn(repoUserFindByCpfMockBehavior(user.getCpf()));
		Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.register(user));
	}
	
	@Test
	public void shouldThrowsExceptionIfEmailIsAlreadyInUse() {
		User user = new User("Robissu", "123456789", "71418894001", "pedro@com.com");
		Mockito.when(userRepository.findByEmail(anyString())).thenReturn(repoUserFindByEmailMockBehavior(user.getEmail()));
		Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.register(user));
	}

	@Test
	public void shouldUpdateEmail() {
		String newEmail = "novo@com.com";
		Long searchedUser = 1l;
		
		Mockito.when(userRepository.findById(anyLong())).thenReturn(repoUserFindOneUserMockBehavior(searchedUser));

		Mockito.when(userRepository.findByEmail(anyString())).thenReturn(repoUserFindByEmailMockBehavior(newEmail));
		
		User user = userService.updateEmail(searchedUser, new UserDtoUpdateEmailRequest(newEmail));
		Assertions.assertEquals(user.getEmail(), newEmail);
	}
	
	public void shouldUpdatePassword() {
		Long searchedUser = 1l;
		String newPass = "java8001";
		Mockito.when(userRepository.findById(anyLong())).thenReturn(repoUserFindOneUserMockBehavior(searchedUser));
		User userOld = repoUserFindOneUserMockBehavior(searchedUser).get();
		User userUpdated = userService.updatePassword(searchedUser, new UserDtoUpdatePasswordRequest(newPass));
		Assertions.assertNotEquals(userOld.getPassword(), userUpdated.getPassword());
	}

	private Optional<User> repoUserFindByCpfMockBehavior(String cpf) {
		List<User> users = fakeUserDB();
		for (User u : users) {
			if (u.getCpf() == cpf)
				return Optional.of(u);
		}

		return Optional.empty();
	}
	
	private Optional<User> repoUserFindByEmailMockBehavior(String email) {
		List<User> users = fakeUserDB();
		for (User u : users) {
			if (u.getEmail() == email)
				return Optional.of(u);
		}

		return Optional.empty();
	}

	private Optional<User> repoUserFindOneUserMockBehavior(Long userId) {
		List<User> users = fakeUserDB();

		for (User u : users) {
			if (u.getId() == userId)
				return Optional.of(u);
		}

		return Optional.empty();
	}

	private List<User> fakeUserDB() {
		List<User> users = new ArrayList<>();

		User user1 = new User("Pedro", "123456789", "73710854008", "pedro@com.com");
		user1.setId(1l);
		users.add(user1);

		User user2 = new User("Maria", "123456789", "07450740060", "pedro@com.com");
		user2.setId(2l);
		users.add(user2);

		return users;
	}
}
