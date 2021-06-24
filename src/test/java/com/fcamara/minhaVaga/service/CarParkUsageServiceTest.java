package com.fcamara.minhaVaga.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import com.fcamara.minhaVaga.exception.CarNotFoundExecption;
import com.fcamara.minhaVaga.exception.IncompatibleVacancyTypeException;
import com.fcamara.minhaVaga.exception.NoMoreVacanciesException;
import com.fcamara.minhaVaga.exception.VehicleAlreadyParkedException;
import com.fcamara.minhaVaga.model.CarParkUsage;
import com.fcamara.minhaVaga.model.Model;
import com.fcamara.minhaVaga.model.TypeOfPayment;
import com.fcamara.minhaVaga.model.TypeOfVehicle;
import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.model.Vacancy;
import com.fcamara.minhaVaga.model.Vehicle;
import com.fcamara.minhaVaga.repository.CarParkAdressVacancyRepository;
import com.fcamara.minhaVaga.repository.CarParkUsageRepository;
import com.fcamara.minhaVaga.repository.VehicleRepository;

public class CarParkUsageServiceTest {

	@Mock
	private CarParkUsageRepository carParkUsageRepository;

	@Mock
	private CarParkAdressVacancyRepository carParkAdressVacancyRepository;

	@Mock
	private VehicleRepository vehicleRepository;

	private CarParkUsageService carParkUsageService;

	@BeforeEach
	public void BeforeEach() {
		MockitoAnnotations.openMocks(this);
		this.carParkUsageService = new CarParkUsageService(carParkUsageRepository, carParkAdressVacancyRepository,
				vehicleRepository);
	}

	@Test
	public void shouldThrowsExceptionIfVehicleIsAlreadyParked() {
		Long searchedVehicle = 2l;

		Mockito.when(vehicleRepository.findVehicleByIdAndUserId(searchedVehicle, 1l))
				.thenReturn(repoVehicleFindVehicleByIdAndUserIdMockBehavior(searchedVehicle));

		Mockito.when(carParkUsageRepository.findByVehicleIdAndExitTimeIsNull(searchedVehicle))
				.thenReturn(repoCarParkUsageFindByVehicleAndExitTimeIsNullMockBehavior(searchedVehicle));

		Assertions.assertThrows(VehicleAlreadyParkedException.class,
				() -> carParkUsageService.insertParking(1l, 1l, searchedVehicle, TypeOfPayment.DIA));
	}

	@Test
	public void shouldThrowsExceptionIfVehicleTypeIsDifferentThanVacancyOne() {
		Long searchedVehicle = 1l;
		Long searchedVacancy = 2l;

		Mockito.when(vehicleRepository.findVehicleByIdAndUserId(searchedVehicle, 1l))
				.thenReturn(repoVehicleFindVehicleByIdAndUserIdMockBehavior(searchedVehicle));

		Mockito.when(carParkUsageRepository.findByVehicleIdAndExitTimeIsNull(searchedVehicle))
				.thenReturn(repoCarParkUsageFindByVehicleAndExitTimeIsNullMockBehavior(searchedVehicle));

		Mockito.when(carParkAdressVacancyRepository.findById(searchedVacancy))
				.thenReturn(repoCarParkUsageFindVacancyByIdMockBehavior(searchedVacancy));

		Assertions.assertThrows(IncompatibleVacancyTypeException.class,
				() -> carParkUsageService.insertParking(1l, searchedVacancy, searchedVehicle, TypeOfPayment.DIA));
	}

	@Test
	public void shouldThrowsExceptionIfVacancyAreFull() {
		Long searchedVehicle = 3l;
		Long searchedVacancy = 2l;

		Mockito.when(vehicleRepository.findVehicleByIdAndUserId(searchedVehicle, 1l))
				.thenReturn(repoVehicleFindVehicleByIdAndUserIdMockBehavior(searchedVehicle));

		Mockito.when(carParkUsageRepository.findByVehicleIdAndExitTimeIsNull(searchedVehicle))
				.thenReturn(repoCarParkUsageFindByVehicleAndExitTimeIsNullMockBehavior(searchedVehicle));

		Mockito.when(carParkAdressVacancyRepository.findById(searchedVacancy))
				.thenReturn(repoCarParkUsageFindVacancyByIdMockBehavior(searchedVacancy));

		Mockito.when(carParkUsageRepository.findByVacancyIdAndExitTimeIsNull(searchedVacancy))
				.thenReturn(repoCarParkUsageFindByVacancyAndExitTimeIsNullListMockBehavior(searchedVacancy));

		Assertions.assertThrows(NoMoreVacanciesException.class,
				() -> carParkUsageService.insertParking(1l, searchedVacancy, searchedVehicle, TypeOfPayment.DIA));
	}

	@Test
	public void shouldSaveAndReturnSavedCarParkUsage() {
		Long searchedVehicle = 1l;
		Long searchedVacancy = 1l;

		Mockito.when(vehicleRepository.findVehicleByIdAndUserId(searchedVehicle, 1l))
				.thenReturn(repoVehicleFindVehicleByIdAndUserIdMockBehavior(searchedVehicle));

		Mockito.when(carParkUsageRepository.findByVehicleIdAndExitTimeIsNull(searchedVehicle))
				.thenReturn(repoCarParkUsageFindByVehicleAndExitTimeIsNullMockBehavior(searchedVehicle));

		Mockito.when(carParkAdressVacancyRepository.findById(searchedVacancy))
				.thenReturn(repoCarParkUsageFindVacancyByIdMockBehavior(searchedVacancy));

		Mockito.when(carParkUsageRepository.findByVacancyIdAndExitTimeIsNull(searchedVacancy))
				.thenReturn(repoCarParkUsageFindByVacancyAndExitTimeIsNullListMockBehavior(searchedVacancy));

		Mockito.when(carParkUsageRepository.save(any(CarParkUsage.class)))
				.thenReturn(repoCarParkUsageSaveMockBehavior());

		CarParkUsage carParkUsage = carParkUsageService.insertParking(1l, searchedVacancy, searchedVehicle,
				TypeOfPayment.DIA);
		verify(carParkUsageRepository, times(1)).save(any(CarParkUsage.class));
		Assertions.assertTrue(carParkUsage instanceof CarParkUsage);
	}

	@Test
	public void shouldThrowsCarNotFoundException() {
		Long searchedVehicle = 1l;
		Long searchedVacancy = 1l;

		Mockito.when(vehicleRepository.findVehicleByIdAndUserId(searchedVehicle, 1l))
				.thenReturn(repoVehicleFindVehicleByIdAndUserIdMockBehavior(searchedVehicle));

		Mockito.when(carParkUsageRepository.findByVehicleIdAndExitTimeIsNull(searchedVehicle))
				.thenReturn(repoCarParkUsageFindByVehicleAndExitTimeIsNullMockBehavior(searchedVehicle));

		Assertions.assertThrows(CarNotFoundExecption.class,
				() -> carParkUsageService.leaveParking(searchedVacancy, searchedVehicle));
	}

	@Test
	public void shouldSetExitTimeAndReturnCarParkUsage() {
		Long searchedVehicle = 2l;
		Long searchedVacancy = 1l;

		Mockito.when(vehicleRepository.findVehicleByIdAndUserId(searchedVehicle, 1l))
				.thenReturn(repoVehicleFindVehicleByIdAndUserIdMockBehavior(searchedVehicle));

		Mockito.when(carParkUsageRepository.findByVehicleIdAndExitTimeIsNull(searchedVehicle))
				.thenReturn(repoCarParkUsageFindByVehicleAndExitTimeIsNullMockBehavior(searchedVehicle));

		CarParkUsage carParkUsage = carParkUsageService.leaveParking(searchedVacancy, searchedVehicle);
		Assertions.assertTrue(carParkUsage instanceof CarParkUsage);
		Assertions.assertNotEquals(carParkUsage.getExitTime(), null);
	}

	@Test
	public void shouldThrowsExceptionIfDateIntervalAreBiggerThanOneYear() {
		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime twoYearsInFuture = ChronoUnit.YEARS.addTo(now, 2);
		Assertions.assertThrows(ResponseStatusException.class,
				() -> carParkUsageService.listWhoEnteredInLastCustomInterval(null, now, twoYearsInFuture, false, null));
	}

	private Optional<CarParkUsage> repoCarParkUsageFindByVehicleAndExitTimeIsNullMockBehavior(Long vehicleId) {
		List<CarParkUsage> fakeDB = fakeUsageDB();
		for (CarParkUsage cpu : fakeDB) {
			if (cpu.getVehicle().getId() == vehicleId && cpu.getExitTime() == null)
				return Optional.of(cpu);
		}
		return Optional.empty();
	}

	private List<CarParkUsage> repoCarParkUsageFindByVacancyAndExitTimeIsNullListMockBehavior(Long vehicleId) {
		List<CarParkUsage> fakeDB = fakeUsageDB();
		List<CarParkUsage> count = new ArrayList<>();
		for (CarParkUsage cpu : fakeDB) {
			if (cpu.getVehicle().getId() == vehicleId && cpu.getExitTime() == null)
				count.add(cpu);
		}
		return count;
	}

	private Optional<Vacancy> repoCarParkUsageFindVacancyByIdMockBehavior(Long vacancyId) {
		List<Vacancy> fakeDB = fakeVacancyDB();
		for (Vacancy v : fakeDB) {
			if (v.getId() == vacancyId)
				return Optional.of(v);
		}
		return Optional.empty();
	}

	private Optional<Vehicle> repoVehicleFindVehicleByIdAndUserIdMockBehavior(Long vehicleId) {
		List<Vehicle> fakeDB = fakeVehicleDB();
		for (Vehicle v : fakeDB) {
			if (v.getId() == vehicleId)
				return Optional.of(v);
		}
		return Optional.empty();
	}

	private CarParkUsage repoCarParkUsageSaveMockBehavior() {
		return new CarParkUsage();
	}

	private List<CarParkUsage> fakeUsageDB() {
		List<Vacancy> vacancies = fakeVacancyDB();

		List<Vehicle> vehicles = fakeVehicleDB();

		CarParkUsage cpu1 = new CarParkUsage(vacancies.get(0), vehicles.get(0), TypeOfPayment.HORA);
		cpu1.exit();
		CarParkUsage cpu2 = new CarParkUsage(vacancies.get(1), vehicles.get(1), TypeOfPayment.MES);

		List<CarParkUsage> usages = new ArrayList<>();

		usages.add(cpu1);
		usages.add(cpu2);
		return usages;

	}

	private List<Vacancy> fakeVacancyDB() {
		Vacancy vac1 = new Vacancy(null, TypeOfVehicle.MOTO, 1, new BigDecimal(5), new BigDecimal(15),
				new BigDecimal(50));
		vac1.setId(1l);

		Vacancy vac2 = new Vacancy(null, TypeOfVehicle.CARRO, 1, new BigDecimal(5), new BigDecimal(15),
				new BigDecimal(50));
		vac2.setId(2l);

		List<Vacancy> vacancies = new ArrayList<>();
		vacancies.add(vac1);
		vacancies.add(vac2);

		return vacancies;

	}

	private List<Vehicle> fakeVehicleDB() {
		Model mod1 = new Model("Lego-75", null, TypeOfVehicle.MOTO);
		Model mod2 = new Model("Lego-52", null, TypeOfVehicle.CARRO);

		User user1 = new User();
		user1.setId(1);

		User user2 = new User();
		user2.setId(2);

		Vehicle vehi1 = new Vehicle(user1, mod1, null, "AAA0123");
		vehi1.setId(1l);
		Vehicle vehi2 = new Vehicle(user2, mod2, null, "AAA4321");
		vehi2.setId(2l);

		Vehicle vehi3 = new Vehicle(user2, mod2, null, "AAA7894");
		vehi3.setId(3l);

		List<Vehicle> vehicles = new ArrayList<>();

		vehicles.add(vehi1);
		vehicles.add(vehi2);
		vehicles.add(vehi3);

		return vehicles;
	}
}
