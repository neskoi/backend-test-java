package com.fcamara.minhaVaga.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.model.CarParkUsage;

@Repository
public interface CarParkUsageRepository extends JpaRepository<CarParkUsage, Long> {
	
	final String carParkUsageBaseQuery =  "SELECT car_park.id as car_park_id, car_park.name, adress.id as adress_id, adress.street, vacancy.id as vacancy_id, vacancy.type_of_vehicle, car_park_usage.* FROM car_park "
			+ " INNER JOIN adress ON adress.car_park_id = car_park.id "
			+ " INNER JOIN vacancy ON vacancy.adress_id = adress.id "
			+ " INNER JOIN car_park_usage ON car_park_usage.vacancy_id = vacancy.id ";
	
	final String entranceBetweenDatesQuery = carParkUsageBaseQuery
			+ " WHERE adress.car_park_id = ?1 AND car_park_usage.entrance_time > ?2 AND car_park_usage.entrance_time < ?3 " 
			+ " ORDER BY car_park_usage.entrance_time DESC";
	
	final String exitBetweenDatesQuery = carParkUsageBaseQuery
			+ " WHERE adress.car_park_id = ?1 AND car_park_usage.exit_time > ?2 AND car_park_usage.exit_time < ?3 " 
			+ " ORDER BY car_park_usage.entrance_time DESC";
	
	public List<CarParkUsage> findByVacancyIdAndExitTimeIsNull(Long id);

	public Long countByVacancyIdAndExitTimeIsNull(Long id);
	
	public Optional<CarParkUsage> findByVehicleIdAndExitTimeIsNull(Long vehicleId);
	
	@Query(value = entranceBetweenDatesQuery, nativeQuery = true)
	public Page<CarParkUsage> allEntrancesBetweenDates(Long carParkId, ZonedDateTime startDate,
			ZonedDateTime endDate, Pageable pageable);
	
	@Query(value = entranceBetweenDatesQuery, nativeQuery = true)
	public List<CarParkUsage> allEntrancesBetweenDates(Long carParkId, ZonedDateTime startDate,
			ZonedDateTime endDate);

	@Query(value = entranceBetweenDatesQuery, nativeQuery = true)
	public Page<CarParkUsage> allLeavesBetweenDates(Long carParkId, ZonedDateTime startDate,
			ZonedDateTime endDate, Pageable pageable);
	
}
