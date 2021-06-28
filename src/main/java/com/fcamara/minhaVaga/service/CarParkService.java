package com.fcamara.minhaVaga.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fcamara.minhaVaga.dto.request.CarParkAdressDtoRequest;
import com.fcamara.minhaVaga.dto.request.CarParkDtoEmailRequest;
import com.fcamara.minhaVaga.dto.request.CarParkDtoPasswordRequest;
import com.fcamara.minhaVaga.dto.request.CarParkDtoPhoneRequest;
import com.fcamara.minhaVaga.dto.request.VacancyDtoRequest;
import com.fcamara.minhaVaga.exception.UserAlreadyExistsException;
import com.fcamara.minhaVaga.model.Adress;
import com.fcamara.minhaVaga.model.CarPark;
import com.fcamara.minhaVaga.model.CarParkUsage;
import com.fcamara.minhaVaga.model.Role;
import com.fcamara.minhaVaga.model.Vacancy;
import com.fcamara.minhaVaga.repository.CarParkAdressRepository;
import com.fcamara.minhaVaga.repository.CarParkAdressVacancyRepository;
import com.fcamara.minhaVaga.repository.CarParkRepository;
import com.fcamara.minhaVaga.repository.CarParkUsageRepository;
import com.fcamara.minhaVaga.repository.RoleRepository;

@Service
public class CarParkService {

	private CarParkRepository carParkRepository;

	private CarParkAdressRepository carParkAdressRepository;

	private CarParkAdressVacancyRepository carParkAdressVacancyRepository;

	private CarParkUsageRepository carParkUsageRepostitory;

	private RoleRepository roleRepository;

	private PasswordEncoder bcrypt;

	@Autowired
	public CarParkService(CarParkRepository carParkRepository, CarParkAdressRepository carParkAdressRepository,
			CarParkAdressVacancyRepository carParkAdressVacancyRepository,
			CarParkUsageRepository carParkUsageRepostitory, RoleRepository roleRepository, PasswordEncoder bcrypt) {
		super();
		this.carParkRepository = carParkRepository;
		this.carParkAdressRepository = carParkAdressRepository;
		this.carParkAdressVacancyRepository = carParkAdressVacancyRepository;
		this.carParkUsageRepostitory = carParkUsageRepostitory;
		this.roleRepository = roleRepository;
		this.bcrypt = bcrypt;
	}

	public CarPark findOneCarPark(Long id) {
		Optional<CarPark> searchedCarPark = carParkRepository.findById(id);
		if (searchedCarPark.isPresent())
			return searchedCarPark.get();
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	public Page<CarPark> listAllCarParks(Pageable pageable) {
		return carParkRepository.findAll(pageable);
	}

	public CarPark register(CarPark carPark) {
		if (isCnpjRegistered(carPark.getCnpj()) || isEmailAlreadyRegistered(carPark.getEmail()))
			throw new UserAlreadyExistsException("Estabelecimento já cadastrado.");
		encodePassword(carPark);
		carPark.getRoles().add(findRoleByName("CARPARK"));
		return carParkRepository.save(carPark);
	}

	@Transactional
	public CarPark registerAdress(Long id, CarParkAdressDtoRequest carParkAdressRequest) {
		CarPark carPark = findOneCarPark(id);
		Adress carParkAdressToRegister = carParkAdressRequest.convertToAdress(carPark);
		List<Adress> adresses = carPark.getAdress();
		adresses.add(carParkAdressToRegister);
		carPark.setAdress(adresses);
		return carPark;
	}

	@Transactional
	public Adress registerAdressVacancy(Long carParkId, Long adressId, VacancyDtoRequest vacancyRequest) {
		Adress adress = findAdress(adressId);
		checkIfAdressIsFromCarParkIfNotThrowsException(adress, carParkId);
		Vacancy vacancyInfo = vacancyRequest.convertToVacancy();
		vacancyInfo.setAdress(adress);
		List<Vacancy> vacancies = adress.getVacancy();
		vacancies.add(vacancyInfo);
		adress.setVacancy(vacancies);
		return adress;
	}

	@Transactional
	public Vacancy updateAdressVacancy(Long carParkId, Long vacancyId, @Valid VacancyDtoRequest vacancyRequest) {
		Vacancy vacancyToModify = findVacancy(vacancyId);
		checkIfAdressIsFromCarParkIfNotThrowsException(vacancyToModify.getAdress(), carParkId);
		Vacancy vacancyInfo = vacancyRequest.convertToVacancy();
		vacancyToModify.update(vacancyInfo);
		return vacancyToModify;
	}

	@Transactional
	public CarPark updateEmail(Long carParkId, @Valid CarParkDtoEmailRequest carParkRequest) {
		CarPark carPark = findOneCarPark(carParkId);
		if (isEmailAlreadyRegistered(carParkRequest.getEmail()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email indisponivel");
		carPark.setEmail(carParkRequest.getEmail());
		return carPark;
	}

	@Transactional
	public CarPark updatePassword(Long carParkId, @Valid CarParkDtoPasswordRequest carParkRequest) {
		CarPark carPark = findCarPark(carParkId);
		carPark.setPassword(carParkRequest.getPassword());
		encodePassword(carPark);
		return carPark;
	}

	@Transactional
	public CarPark updatePhone(Long carParkId, @Valid CarParkDtoPhoneRequest carParkRequest) {
		CarPark carPark = findCarPark(carParkId);
		carPark.setPhone(carParkRequest.getPhone());
		return carPark;
	}

	@Transactional
	public void deleteAdress(Long carParkId, Long adressId) {
		Adress adress = findAdress(adressId);
		checkIfAdressIsFromCarParkIfNoThrowsException(carParkId, adress);
		for(Vacancy vacancy : adress.getVacancy()) {
			checkIfVacancyIsAlreadyFreeIfNoThrowsException(vacancy.getId());
		}
		adress.setIsActive(false);
		return;
	}

	@Transactional
	public void deleteAdressVacancy(Long carParkId, Long vacancyId) {
		Vacancy vacancy = findVacancy(vacancyId);
		checkIfVacancyIsFromCarParkIfNoThrowsException(carParkId, vacancy);
		checkIfVacancyIsAlreadyFreeIfNoThrowsException(vacancyId);
		vacancy.setIsActive(false);
		return;
	}

	private void checkIfAdressIsFromCarParkIfNoThrowsException(Long carParkId, Adress adress) {
		Long ownerOfAdress = adress.getCarPark().getId();
		if (ownerOfAdress != carParkId) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id invalido");		
	}
		
	private void checkIfVacancyIsFromCarParkIfNoThrowsException(Long carParkId, Vacancy vacancy) {
		Long ownerOfAdressVacancy = vacancy.getAdress().getCarPark().getId();
		if (ownerOfAdressVacancy != carParkId)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id invalido");
	}

	private void checkIfVacancyIsAlreadyFreeIfNoThrowsException(Long vacancyId) {
		Optional<CarParkUsage> vacancyUsage = carParkUsageRepostitory.findByVacancyIdAndExitTimeIsNull(vacancyId);
		if (vacancyUsage.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Ainda existem veiculos estacionados neste endereço");
	}

	private CarPark findCarPark(Long carparkId) {
		Optional<CarPark> searchedCarPark = carParkRepository.findById(carparkId);
		if (searchedCarPark.isPresent())
			return searchedCarPark.get();
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	private Adress findAdress(Long adressId) {
		Optional<Adress> searchedAdress = carParkAdressRepository.findById(adressId);
		if (searchedAdress.isPresent())
			return searchedAdress.get();
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	private Vacancy findVacancy(Long vacancyId) {
		Optional<Vacancy> searchedVacancy = carParkAdressVacancyRepository.findById(vacancyId);
		if (searchedVacancy.isPresent())
			return searchedVacancy.get();
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	private Role findRoleByName(String name) {
		Optional<Role> role = roleRepository.findByName(name);
		if (role.isPresent())
			return role.get();
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role invalida.");
	}

	private void checkIfAdressIsFromCarParkIfNotThrowsException(Adress adress, Long carParkId) {
		Long ownerOfAdressVacancy = adress.getCarPark().getId();
		if (ownerOfAdressVacancy != carParkId)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	private void encodePassword(CarPark carPark) {
		carPark.setPassword(bcrypt.encode(carPark.getPassword()));
	}

	private boolean isCnpjRegistered(String cnpj) {

		try {
			Optional<CarPark> cnpjRegistered = carParkRepository.findByCnpj(cnpj);
			if (cnpjRegistered.isPresent())
				return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Um erro inexperado ocorreu, tente novamente mais tarde.");
		}
		return false;
	}

	private boolean isEmailAlreadyRegistered(String email) {
		try {
			Optional<CarPark> emailRegistered = carParkRepository.findByEmail(email);
			if (emailRegistered.isPresent())
				return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Um erro inexperado ocorreu, tente novamente mais tarde.");
		}
		return false;
	}

}
