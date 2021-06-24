package com.fcamara.minhaVaga.service;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fcamara.minhaVaga.dto.request.VehicleDtoRequest;
import com.fcamara.minhaVaga.model.Color;
import com.fcamara.minhaVaga.model.Model;
import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.model.Vehicle;
import com.fcamara.minhaVaga.repository.ColorRepository;
import com.fcamara.minhaVaga.repository.ModelRepository;
import com.fcamara.minhaVaga.repository.UserRepository;
import com.fcamara.minhaVaga.repository.VehicleRepository;

@Service
public class VehicleService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private ColorRepository colorRepository;

	@Autowired
	private ModelRepository modelRepository;

	public Vehicle findOneVehicle(Long id) {
		Optional<Vehicle> searchedVehicle = vehicleRepository.findById(id);
		if (searchedVehicle.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id invalido.");
		return searchedVehicle.get();
	}

	public Vehicle registerVehicle(Long userId, @Valid VehicleDtoRequest vehicleRequest) {
		Optional<Vehicle> searchedVehicle = vehicleRepository.findByPlate(vehicleRequest.getPlate());
		if (searchedVehicle.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Placa já registrada.");
		Optional<User> searchedUser = userRepository.findById(userId);
		if (searchedUser.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id invalido.");
		Optional<Color> searchedColor = colorRepository.findById(vehicleRequest.getColorId());
		Optional<Model> searchedModel = modelRepository.findById(vehicleRequest.getModelId());
		if (searchedColor.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cor invalida.");
		if (searchedModel.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modelo invalido.");

		Vehicle vehicleToRegister = vehicleRequest.convertToVehicle(searchedUser.get(), searchedColor.get(),
				searchedModel.get());

		return vehicleRepository.save(vehicleToRegister);

	}

	@Transactional
	public Vehicle changeVehicleColor(Long userId, Long vehicleId, Long colorId) {
		Vehicle vehicle = this.findVehicle(vehicleId, userId);
		Color color = this.findColor(colorId);
		vehicle.setColor(color);
		return vehicle;
	}

	public void deleteVehicle(Long vehicleId, Long userId) {
		this.findVehicle(vehicleId, userId);
		vehicleRepository.deleteById(vehicleId);
	}

	private Vehicle findVehicle(Long vehicleId, Long userId) {
		Optional<Vehicle> searchedVehicle = vehicleRepository.findByIdAndUserId(vehicleId, userId);
		if (searchedVehicle.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veiculo invalido.");
		return searchedVehicle.get();
	}
	
	private Color findColor(Long colorId) {
		Optional<Color> searchedColor = colorRepository.findById(colorId);
		if (searchedColor.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id de cor invalido.");
		return searchedColor.get();
	}
}
