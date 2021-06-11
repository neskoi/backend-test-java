package com.fcamara.minhaVaga.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fcamara.minhaVaga.dto.request.VehicleDtoRequest;
import com.fcamara.minhaVaga.model.Vehicle;
import com.fcamara.minhaVaga.repository.ColorRepository;
import com.fcamara.minhaVaga.repository.ModeloRepository;
import com.fcamara.minhaVaga.repository.VehicleRepository;

@Service
public class VehicleService {
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	ColorRepository colorRepository;
	
	@Autowired
	ModeloRepository modelRepository;
	

	public Vehicle registerVehicle(Long userId, @Valid VehicleDtoRequest vehicleRequest) {
		//Optional<Color> searchedColor = vehicleRepository
		return null;
	}
	
}
