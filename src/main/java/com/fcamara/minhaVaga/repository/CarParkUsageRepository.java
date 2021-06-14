package com.fcamara.minhaVaga.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcamara.minhaVaga.model.CarParkUsage;

@Repository
public interface CarParkUsageRepository extends JpaRepository<CarParkUsage, Long> {

	public List<CarParkUsage> findByVacancyIdAndExitTimeIsNull(Long id);

	public Optional<CarParkUsage> findByVehicleIdAndExitTimeIsNull(Long vehicleId);

}
