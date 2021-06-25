package com.fcamara.minhaVaga.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.*;

import com.fcamara.minhaVaga.model.CarPark;
import com.fcamara.minhaVaga.model.Role;
import com.fcamara.minhaVaga.repository.CarParkAdressRepository;
import com.fcamara.minhaVaga.repository.CarParkAdressVacancyRepository;
import com.fcamara.minhaVaga.repository.CarParkRepository;
import com.fcamara.minhaVaga.repository.RoleRepository;
import com.fcamara.minhaVaga.dto.request.CarParkDtoEmailRequest;
import com.fcamara.minhaVaga.dto.request.CarParkDtoPasswordRequest;
import com.fcamara.minhaVaga.dto.request.CarParkDtoPhoneRequest;
import com.fcamara.minhaVaga.exception.UserAlreadyExistsException;

public class CarParkServiceTest {

	@Mock
	private CarParkRepository carParkRepository;

	@Mock
	private CarParkAdressRepository carParkAdressRepository;

	@Mock
	private CarParkAdressVacancyRepository carParkAdressVacancyRepository;
	
	@Mock
	private RoleRepository roleRepository;


	// @Autowired nao funcionou e ficou retornado null, nao entendi o motivo
	private PasswordEncoder bcrypt;

	private CarParkService carParkService;

	@BeforeEach
	public void BeforeEach() {
		MockitoAnnotations.openMocks(this);
		this.bcrypt = new BCryptPasswordEncoder();
		this.carParkService = new CarParkService(carParkRepository, carParkAdressRepository,
				carParkAdressVacancyRepository, roleRepository, bcrypt);
	}

	@Test
	public void shouldReturnOneCarPark() {
		Long idToSearch = 1l;
		Mockito.when(carParkRepository.findById(idToSearch)).thenReturn(repoCarParkFindOneMockBehavior(idToSearch));
		CarPark carPark = carParkService.findOneCarPark(idToSearch);
		Assertions.assertTrue(carPark instanceof CarPark);
	}

	@Test
	public void shouldThrowsExceptionIfInvalidId() {
		Long idToSearch = 5l;
		Mockito.when(carParkRepository.findById(idToSearch)).thenReturn(repoCarParkFindOneMockBehavior(idToSearch));
		Assertions.assertThrows(ResponseStatusException.class, () -> carParkService.findOneCarPark(idToSearch));
	}

	@Test
	public void shouldThrowsExceptionIfCpfOrEmailIsAlreadyInUse() {
		CarPark carPark = new CarPark("Estacionamento 01", "123456789", "31477931000109", "estacionameto01@com.com",
				"40028922");
		Mockito.when(carParkRepository.findByCnpj(carPark.getCnpj()))
				.thenReturn(repoCarParkFindCnpjMockBehavior(carPark.getCnpj()));
		Mockito.when(carParkRepository.findByEmail(carPark.getEmail()))
				.thenReturn(repoCarParkFindEmailMockBehavior(carPark.getEmail()));
		Assertions.assertThrows(UserAlreadyExistsException.class, () -> carParkService.register(carPark));
	}

	@Test
	public void shouldReturnTheSavedCarPark() {
		CarPark carPark = new CarPark("Estacionamento 03", "123456789", "31477931000107", "estacionameto03@com.com",
				"40028922");
		Mockito.when(carParkRepository.findByCnpj(carPark.getCnpj()))
				.thenReturn(repoCarParkFindCnpjMockBehavior(carPark.getCnpj()));
		Mockito.when(carParkRepository.findByEmail(carPark.getEmail()))
				.thenReturn(repoCarParkFindEmailMockBehavior(carPark.getEmail()));
		Mockito.when(roleRepository.findByName(any())).thenReturn(repoRoleFindByNameMockBehavior("CARPARK"));
		Mockito.when(carParkRepository.save(carPark)).thenReturn(repoCarParkSaveMockBehavior(carPark));
		CarPark savedCarPark = carParkService.register(carPark);
		Assertions.assertTrue(savedCarPark instanceof CarPark);
	}

	//Qual seria um teste adequado para registerAdress e registerVacancy?
	
	@Test
	public void shouldReturnExceptionIfNewEmailIsAlreadyInUseWhenUpdate() {
		Long idToSearch = 1l;
		Mockito.when(carParkRepository.findById(idToSearch)).thenReturn(repoCarParkFindOneMockBehavior(idToSearch));
		Mockito.when(carParkRepository.findByEmail("estacionameto01@com.com"))
				.thenReturn(repoCarParkFindEmailMockBehavior("estacionameto01@com.com"));
		Assertions.assertThrows(ResponseStatusException.class,
				() -> carParkService.updateEmail(idToSearch, new CarParkDtoEmailRequest("estacionameto01@com.com")));
	}

	@Test
	public void shouldReturnCarParkWithUpdatedEmail() {
		Long idToSearch = 1l;
		Mockito.when(carParkRepository.findById(idToSearch)).thenReturn(repoCarParkFindOneMockBehavior(idToSearch));
		Mockito.when(carParkRepository.findByEmail("estacionameto010@com.com"))
				.thenReturn(repoCarParkFindEmailMockBehavior("estacionameto010@com.com"));

		CarPark carParkWithUpdatedEmail = carParkService.updateEmail(idToSearch,
				new CarParkDtoEmailRequest("estacionameto010@com.com"));
		Assertions.assertEquals(carParkWithUpdatedEmail.getEmail(), "estacionameto010@com.com");
	}
	
	@Test
	public void shouldReturnCarParkWithUpdatedHashedPassword() {
		Long idToSearch = 1l;
		Mockito.when(carParkRepository.findById(idToSearch)).thenReturn(repoCarParkFindOneMockBehavior(idToSearch));
		

		CarPark carParkWithUpdatedPass = carParkService.updatePassword(idToSearch,
				new CarParkDtoPasswordRequest("987654321"));
		
		Assertions.assertNotEquals(carParkWithUpdatedPass.getPassword(), "123456789");
		Assertions.assertNotEquals(carParkWithUpdatedPass.getPassword(), "987654321");
	}
	
	@Test
	public void shouldReturnCarParkWithUpdatedPhone() {
		Long idToSearch = 1l;
		Mockito.when(carParkRepository.findById(idToSearch)).thenReturn(repoCarParkFindOneMockBehavior(idToSearch));

		CarPark carParkWithUpdatedEmail = carParkService.updatePhone(idToSearch,
				new CarParkDtoPhoneRequest("1234567890"));
		Assertions.assertEquals(carParkWithUpdatedEmail.getPhone(), "1234567890");
	}

	private CarPark repoCarParkSaveMockBehavior(CarPark carPark) {
		carPark.setId(3l);
		return carPark;
	}

	private Optional<CarPark> repoCarParkFindOneMockBehavior(Long carParkId) {
		List<CarPark> carParks = carParkList();
		for (CarPark cp : carParks) {
			if (cp.getId() == carParkId)
				return Optional.of(cp);
		}
		return Optional.empty();
	}

	private Optional<CarPark> repoCarParkFindCnpjMockBehavior(String cnpj) {
		List<CarPark> carParks = carParkList();
		for (CarPark cp : carParks) {
			if (cp.getCnpj() == cnpj)
				return Optional.of(cp);
		}
		return Optional.empty();
	}

	private Optional<CarPark> repoCarParkFindEmailMockBehavior(String email) {
		List<CarPark> carParks = carParkList();
		for (CarPark cp : carParks) {
			if (cp.getEmail() == email)
				return Optional.of(cp);
		}
		return Optional.empty();
	}

	private Optional<Role> repoRoleFindByNameMockBehavior(String name){
		List<Role> roles = fakeRolesDB();
		
		for(Role r : roles) {
			if(r.getName() == name)
				return Optional.of(r);
		}
		return Optional.empty();
	}
	
	private List<CarPark> carParkList() {
		List<CarPark> carParks = new ArrayList<>();
		CarPark cp1 = new CarPark("Estacionamento 01", "123456789", "31477931000109", "estacionameto01@com.com",
				"40028922");
		cp1.setId(1l);
		CarPark cp2 = new CarPark("Estacionamento 02", "123456789", "57498821000117", "estacionameto02@com.com",
				"40028923");
		cp2.setId(2l);
		carParks.add(cp1);
		carParks.add(cp2);
		return carParks;
	}
	
	private List<Role> fakeRolesDB(){
		List<Role> roles = new ArrayList<>();
		roles.add(new Role("CARPARK"));
		roles.add(new Role("CAROWNER"));
		return roles;
	}
}
