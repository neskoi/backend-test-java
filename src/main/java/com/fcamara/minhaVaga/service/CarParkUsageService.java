package com.fcamara.minhaVaga.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fcamara.minhaVaga.model.CarParkUsage;
import com.fcamara.minhaVaga.model.TypeOfPayment;
import com.fcamara.minhaVaga.model.Vacancy;
import com.fcamara.minhaVaga.model.Vehicle;
import com.fcamara.minhaVaga.repository.CarParkAdressVacancyRespository;
import com.fcamara.minhaVaga.repository.CarParkUsageRepository;
import com.fcamara.minhaVaga.repository.VehicleRepository;

@Service
public class CarParkUsageService {
	@Autowired
	CarParkUsageRepository carParkUsageRepository;

	@Autowired
	CarParkAdressVacancyRespository carParkAdressVacancyRepository;

	@Autowired
	VehicleRepository vehicleRepository;

	private int howManyFreeVacanciesOneCarParkHave(Vacancy vacancy) {
		// TODO reduzir trafego da query - DB deve devolver apenas o numero de rows e
		// não todas elas para contar aqui.
		List<CarParkUsage> inUseVacancies = carParkUsageRepository.findByVacancyIdAndExitTimeIsNull(vacancy.getId());
		return vacancy.getAmount() - inUseVacancies.size();
	}

	public CarParkUsage insertParking(Long vacancyId, Long vehicleId, TypeOfPayment typeOfPayment) {
		// Talvez fosse interessante adicionar um observer
		// para que o estacionamento fosse notificado com a entrada de cada usuario,
		// assim poderia atualizar o limite de vagas no client side
		//Como reduzir essa função?
		Optional<CarParkUsage> searchedCarParkUsage = carParkUsageRepository.findByVehicleIdAndExitTimeIsNull(vehicleId);
		if (searchedCarParkUsage.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veiculo já estacionado.");
		Vacancy vacancy = findVacancyById(vacancyId);
		Vehicle vehicle = findVehicleById(vehicleId);
		if (vacancy.getTypeOfVehicle() == vehicle.getModel().getTypeOfVehicle()) {
			int leftVacancies = howManyFreeVacanciesOneCarParkHave(vacancy);
			if (leftVacancies > 0) {
				CarParkUsage carParkUsageToRegister = createCarParkUsage(vacancy, vehicle, typeOfPayment);
				return carParkUsageRepository.save(carParkUsageToRegister);
			}
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "As vagas desse tipo estão lotadas.");
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veiculo não compativel com o tipo de vaga.");
	}

	@Transactional
	public CarParkUsage leaveParking(Long vehicleId) {
		Optional<CarParkUsage> searchedCarParkUsage = carParkUsageRepository.findByVehicleIdAndExitTimeIsNull(vehicleId);
		if (searchedCarParkUsage.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veiculo não localizado.");
		CarParkUsage carParkUsage = searchedCarParkUsage.get();
		carParkUsage.exit();
		return carParkUsage;
	}
	
 	private CarParkUsage createCarParkUsage(Vacancy vacancy, Vehicle vehicle, TypeOfPayment typeOfPayment) {
		return new CarParkUsage(vacancy, vehicle, typeOfPayment);
	}

	private Vacancy findVacancyById(Long vacancyId) {
		Optional<Vacancy> searchedVacancy = carParkAdressVacancyRepository.findById(vacancyId);
		if (searchedVacancy.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vaga não localizada.");
		return searchedVacancy.get();
	}

	private Vehicle findVehicleById(Long vehicleId) {
		Optional<Vehicle> searchedVehicle = vehicleRepository.findById(vehicleId);
		if (searchedVehicle.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veiculo não localizado.");
		return searchedVehicle.get();
	}

}
