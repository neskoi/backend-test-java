package com.fcamara.minhaVaga.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fcamara.minhaVaga.dto.response.CarParkUsageDtoResponse;
import com.fcamara.minhaVaga.dto.response.ReportDtoResponse;
import com.fcamara.minhaVaga.model.CarParkUsage;
import com.fcamara.minhaVaga.model.TypeOfPayment;
import com.fcamara.minhaVaga.model.Vacancy;
import com.fcamara.minhaVaga.model.Vehicle;
import com.fcamara.minhaVaga.repository.CarParkAdressVacancyRespository;
import com.fcamara.minhaVaga.repository.CarParkUsageRepository;
import com.fcamara.minhaVaga.repository.VehicleRepository;
import com.fcamara.minhaVaga.util.ChronoMath;
import com.fcamara.minhaVaga.util.TimeSpaces;

@Service
public class CarParkUsageService {
	@Autowired
	CarParkUsageRepository carParkUsageRepository;

	@Autowired
	CarParkAdressVacancyRespository carParkAdressVacancyRepository;

	@Autowired
	VehicleRepository vehicleRepository;

	public CarParkUsage insertParking(Long vacancyId, Long vehicleId, TypeOfPayment typeOfPayment) {
		// Talvez fosse interessante adicionar um observer
		// para que o estacionamento fosse notificado com a entrada de cada usuario,
		// assim poderia atualizar o limite de vagas no client side
		// Como reduzir essa função?
		Optional<CarParkUsage> searchedCarParkUsage = carParkUsageRepository
				.findByVehicleIdAndExitTimeIsNull(vehicleId);
		if (searchedCarParkUsage.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veiculo já estacionado.");
		Vacancy vacancy = findVacancyById(vacancyId);
		Vehicle vehicle = findVehicleById(vehicleId);
		if (vacancy.getTypeOfVehicle() == vehicle.getModel().getTypeOfVehicle()) {
			int leftVacancies = howManyFreeVacanciesOneCarParkHave(vacancy);
			if (leftVacancies > 0) {
				CarParkUsage carParkUsageToRegister = new CarParkUsage(vacancy, vehicle, typeOfPayment);
				return carParkUsageRepository.save(carParkUsageToRegister);
			}
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "As vagas desse tipo estão lotadas.");
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veiculo não compativel com o tipo de vaga.");
	}

	@Transactional
	public CarParkUsage leaveParking(Long vehicleId) {
		Optional<CarParkUsage> searchedCarParkUsage = carParkUsageRepository
				.findByVehicleIdAndExitTimeIsNull(vehicleId);
		if (searchedCarParkUsage.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veiculo não localizado.");
		CarParkUsage carParkUsage = searchedCarParkUsage.get();
		carParkUsage.exit();
		return carParkUsage;
	}

	public Page<CarParkUsageDtoResponse> listWhoEnteredInLastCustomInterval(Long carParkId, ZonedDateTime entranceTime,
			ZonedDateTime exitTime, boolean leaves, Pageable pageable) {

		if (ChronoMath.hasMoreThanAYearBetweenDates(entranceTime, exitTime))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Intervalo superior a um ano rejeitado.");

		
		Page<CarParkUsage> carParkUsages;
		if (leaves) {
			System.out.println("saidas");
			carParkUsages = carParkUsageRepository.allLeavesBetweenDates(carParkId, entranceTime, exitTime, pageable);
		} else {
			System.out.println("entradas");
			carParkUsages = carParkUsageRepository.allEntrancesBetweenDates(carParkId, entranceTime, exitTime,
					pageable);
		}

		return CarParkUsageDtoResponse.bulkConvertToDtoResponse(carParkUsages);
	}

	public Page<CarParkUsageDtoResponse> listWhoEnteredInLastChoosedInterval(Long carParkId, TimeSpaces interval,
			boolean leaves, Pageable pageable) {
		Page<CarParkUsage> carParkUsages;
		if (leaves) {
			carParkUsages = carParkUsageRepository.allLeavesBetweenDates(carParkId, interval.getInitialTime(),
					interval.getFinalTime(), pageable);
		} else {
			carParkUsages = carParkUsageRepository.allEntrancesBetweenDates(carParkId, interval.getInitialTime(),
					interval.getFinalTime(), pageable);
		}
		return CarParkUsageDtoResponse.bulkConvertToDtoResponse(carParkUsages);
	}

	public ReportDtoResponse reportLastChoosedInterval(Long carParkId, TimeSpaces reportCreator) {
		List<CarParkUsage> dataToAnalyse = carParkUsageRepository.allEntrancesBetweenDates(carParkId,
				reportCreator.getInitialTime(), reportCreator.getFinalTime());
		return ReportDtoResponse.createReport(dataToAnalyse);
	}

	public ReportDtoResponse reportCustomInterval(Long carParkId, ZonedDateTime entranceTime, ZonedDateTime exitTime) {
		if (ChronoMath.hasMoreThanAYearBetweenDates(entranceTime, exitTime))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Intervalo superior a um ano rejeitado.");
		List<CarParkUsage> dataToAnalyse = carParkUsageRepository.allEntrancesBetweenDates(carParkId, entranceTime,
				exitTime);
		return ReportDtoResponse.createReport(dataToAnalyse);
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

	private int howManyFreeVacanciesOneCarParkHave(Vacancy vacancy) {
		// TODO reduzir trafego da query - DB deve devolver apenas o numero de rows e
		// não todas elas para contar aqui.
		List<CarParkUsage> inUseVacancies = carParkUsageRepository.findByVacancyIdAndExitTimeIsNull(vacancy.getId());
		return vacancy.getAmount() - inUseVacancies.size();
	}

}
