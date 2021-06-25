package com.fcamara.minhaVaga.service;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.fcamara.minhaVaga.model.Role;
import com.fcamara.minhaVaga.model.Vacancy;
import com.fcamara.minhaVaga.repository.CarParkAdressRepository;
import com.fcamara.minhaVaga.repository.CarParkAdressVacancyRepository;
import com.fcamara.minhaVaga.repository.CarParkRepository;
import com.fcamara.minhaVaga.repository.RoleRepository;

@Service
public class CarParkService {

	private CarParkRepository carParkRepository;

	private CarParkAdressRepository carParkAdressRepository;

	private CarParkAdressVacancyRepository carParkAdressVacancyRepository;

	private RoleRepository roleRepository;
	
	private PasswordEncoder bcrypt;

	@Autowired
	public CarParkService(CarParkRepository carParkRepository, CarParkAdressRepository carParkAdressRepository,
			CarParkAdressVacancyRepository carParkAdressVacancyRepository, RoleRepository roleRepository, PasswordEncoder bcrypt) {
		super();
		this.carParkRepository = carParkRepository;
		this.carParkAdressRepository = carParkAdressRepository;
		this.carParkAdressVacancyRepository = carParkAdressVacancyRepository;
		this.roleRepository = roleRepository;
		this.bcrypt = bcrypt;
	}

	public CarPark findOneCarPark(Long id) {
		Optional<CarPark> searchedCarPark = carParkRepository.findById(id);
		if (searchedCarPark.isPresent())
			return searchedCarPark.get();
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
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
		carPark.getAdress().add(carParkAdressToRegister);
		return carPark;
	}

	@Transactional
	public Adress registerAdressVacancy(Long carParkId, Long adressId, VacancyDtoRequest vacancyRequest) {
		Adress adress = findAdress(adressId);
		Long ownerOfAdress = adress.getCarPark().getId();
		if (ownerOfAdress != carParkId)
			checkIfAdressIsFromCarParkIfNotThrowsException(adress, carParkId);
		Vacancy vacancyInfo = vacancyRequest.convertToVacancy();
		vacancyInfo.setAdress(adress);
		adress.getVacancy().add(vacancyInfo);
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

	public void deleteAdress(Long carParkId, Long adressId) {
		Optional<Adress> searchedAdress = carParkAdressRepository.findById(adressId);
		if (searchedAdress.isPresent()) {
			Adress adressToDelete = searchedAdress.get();
			Long ownerOfAdress = adressToDelete.getCarPark().getId();
			if (ownerOfAdress == carParkId) {
				carParkAdressRepository.delete(adressToDelete);
				return;
			}
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	public void deleteAdressVacancy(Long carParkId, Long vacancyId) {
		Optional<Vacancy> searchedVacancy = carParkAdressVacancyRepository.findById(vacancyId);
		if (searchedVacancy.isPresent()) {
			Vacancy vacancyToDelete = searchedVacancy.get();
			Long ownerOfAdressVacancy = vacancyToDelete.getAdress().getCarPark().getId();
			if (ownerOfAdressVacancy == carParkId) {
				carParkAdressVacancyRepository.deleteById(vacancyToDelete.getId());
				return;
			}
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
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
		if(role.isPresent())
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
