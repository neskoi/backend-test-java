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
import com.fcamara.minhaVaga.exception.CarNotFoundExecption;
import com.fcamara.minhaVaga.exception.IncompatibleVacancyTypeException;
import com.fcamara.minhaVaga.exception.NoMoreVacanciesException;
import com.fcamara.minhaVaga.exception.VehicleAlreadyParkedException;
import com.fcamara.minhaVaga.model.CarParkUsage;
import com.fcamara.minhaVaga.model.TypeOfPayment;
import com.fcamara.minhaVaga.model.Vacancy;
import com.fcamara.minhaVaga.model.Vehicle;
import com.fcamara.minhaVaga.repository.CarParkAdressVacancyRepository;
import com.fcamara.minhaVaga.repository.CarParkUsageRepository;
import com.fcamara.minhaVaga.repository.VehicleRepository;
import com.fcamara.minhaVaga.util.ChronoMath;
import com.fcamara.minhaVaga.util.TimeSpaces;

@Service
public class CarParkUsageService {

	private CarParkUsageRepository carParkUsageRepository;

	private CarParkAdressVacancyRepository carParkAdressVacancyRepository;

	private VehicleRepository vehicleRepository;
	
	@Autowired
	public CarParkUsageService(CarParkUsageRepository carParkUsageRepository,
			CarParkAdressVacancyRepository carParkAdressVacancyRepository, VehicleRepository vehicleRepository) {
		super();
		this.carParkUsageRepository = carParkUsageRepository;
		this.carParkAdressVacancyRepository = carParkAdressVacancyRepository;
		this.vehicleRepository = vehicleRepository;
	}

	public CarParkUsage insertParking(Long userId, Long vacancyId, Long vehicleId, TypeOfPayment typeOfPayment) {
		
		Vehicle vehicle = this.findVehicleByIdAndUserId(vehicleId, userId);

		if (this.checkIfVehicleIsAlreadyParked(vehicleId))
			throw new VehicleAlreadyParkedException("Veiculo já estacionado.");

		Vacancy vacancy = findVacancyById(vacancyId);

		if (vacancy.getTypeOfVehicle() != vehicle.getModel().getTypeOfVehicle())
			throw new IncompatibleVacancyTypeException("Veiculo não compativel com o tipo de vaga.");

		int leftVacancies = howManyFreeVacanciesOneCarParkHave(vacancy);

		if (leftVacancies <= 0) 
			throw new NoMoreVacanciesException("As vagas desse tipo estão lotadas.");
		
		CarParkUsage carParkUsageToRegister = new CarParkUsage(vacancy, vehicle, typeOfPayment);
		return carParkUsageRepository.save(carParkUsageToRegister);
	}

	@Transactional
	public CarParkUsage leaveParking(Long userId, Long vehicleId) {
		
		this.findVehicleByIdAndUserId(vehicleId, userId);
		
		Optional<CarParkUsage> searchedCarParkUsage = carParkUsageRepository.findByVehicleIdAndExitTimeIsNull(vehicleId);
		if (searchedCarParkUsage.isEmpty())
			throw new CarNotFoundExecption("Veiculo não localizado.");
		
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
			carParkUsages = carParkUsageRepository.allLeavesBetweenDates(carParkId, entranceTime, exitTime, pageable);
		} else {
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

	private boolean checkIfVehicleIsAlreadyParked(Long vehicleId) {
		Optional<CarParkUsage> searchedCarParkUsage = carParkUsageRepository
				.findByVehicleIdAndExitTimeIsNull(vehicleId);
		return searchedCarParkUsage.isPresent();
	}
	
	private Vacancy findVacancyById(Long vacancyId) {
		Optional<Vacancy> searchedVacancy = carParkAdressVacancyRepository.findById(vacancyId);
		if (searchedVacancy.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vaga não localizada.");
		return searchedVacancy.get();
	}

	private Vehicle findVehicleByIdAndUserId(Long vehicleId, Long userId) {
		Optional<Vehicle> searchedVehicle = vehicleRepository.findVehicleByIdAndUserId(vehicleId, userId);
		if (searchedVehicle.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veiculo ou usuário invalido.");
		return searchedVehicle.get();
	}

	private int howManyFreeVacanciesOneCarParkHave(Vacancy vacancy) {
		// TODO reduzir trafego da query - DB deve devolver apenas o numero de rows e
		// não todas elas para contar aqui.
		List<CarParkUsage> inUseVacancies = carParkUsageRepository.findByVacancyIdAndExitTimeIsNull(vacancy.getId());
		return vacancy.getAmount() - inUseVacancies.size();
	}

}
