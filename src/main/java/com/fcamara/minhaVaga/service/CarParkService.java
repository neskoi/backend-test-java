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
import com.fcamara.minhaVaga.model.Vacancy;
import com.fcamara.minhaVaga.repository.CarParkAdressRespository;
import com.fcamara.minhaVaga.repository.CarParkAdressVacancyRespository;
import com.fcamara.minhaVaga.repository.CarParksRepository;

@Service
public class CarParkService {

	@Autowired
	CarParksRepository carParkRepository;

	@Autowired
	CarParkAdressRespository carParkAdressRepository;

	@Autowired
	CarParkAdressVacancyRespository carParkAdressVacancyRepository;

	@Autowired
	PasswordEncoder bcrypt;

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
		Optional<Adress> searchedAdress = carParkAdressRepository.findById(adressId);
		if (searchedAdress.isPresent()) {
			Adress adress = searchedAdress.get();
			Long ownerOfAdress = adress.getCarPark().getId();
			if (ownerOfAdress == carParkId) {
				Vacancy vacancyInfo = vacancyRequest.convertToVacancy();
				vacancyInfo.setAdress(adress);
				adress.getVacancy().add(vacancyInfo);
				return adress;
			}
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	@Transactional
	public Vacancy updateAdressVacancy(Long carParkId, Long vacancyId, @Valid VacancyDtoRequest vacancyRequest) {
		Optional<Vacancy> searchedVacancy = carParkAdressVacancyRepository.findById(vacancyId);
		if (searchedVacancy.isPresent()) {
			Vacancy vacancyToModify = searchedVacancy.get();
			Long ownerOfAdressVacancy = vacancyToModify.getAdress().getCarPark().getId();
			if (ownerOfAdressVacancy == carParkId) {
				Vacancy vacancyInfo = vacancyRequest.convertToVacancy();
				vacancyToModify.update(vacancyInfo);
				return vacancyToModify;
			}
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	@Transactional
	public CarPark updateEmail(Long id, @Valid CarParkDtoEmailRequest carParkRequest) {
		Optional<CarPark> searchedCarPark = carParkRepository.findById(id);
		if (searchedCarPark.isPresent()) {
			CarPark carPark = searchedCarPark.get();
			if (isEmailAlreadyRegistered(carParkRequest.getEmail()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email indisponivel");
			carPark.setEmail(carParkRequest.getEmail());
			return carPark;
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	@Transactional
	public CarPark updatePassword(Long id, @Valid CarParkDtoPasswordRequest carParkRequest) {
		Optional<CarPark> searchedCarPark = carParkRepository.findById(id);
		if (searchedCarPark.isPresent()) {
			CarPark carPark = searchedCarPark.get();
			carPark.setPassword(carParkRequest.getPassword());
			encodePassword(carPark);
			return carPark;
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido.");
	}

	@Transactional
	public CarPark updatePhone(Long id, @Valid CarParkDtoPhoneRequest carParkRequest) {
		Optional<CarPark> searchedCarPark = carParkRepository.findById(id);
		if (searchedCarPark.isPresent()) {
			CarPark carPark = searchedCarPark.get();
			carPark.setPhone(carParkRequest.getPhone());
			return carPark;
		}

		return null;
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

	private void encodePassword(CarPark carPark) {
		carPark.setPassword(bcrypt.encode(carPark.getPassword()));
	}

	private boolean isCnpjRegistered(String cnpj) {

		try {
			CarPark cnpjRegistered = carParkRepository.findByCnpj(cnpj);
			if (cnpjRegistered != null)
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
