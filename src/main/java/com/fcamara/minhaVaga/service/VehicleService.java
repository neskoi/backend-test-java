package com.fcamara.minhaVaga.service;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fcamara.minhaVaga.dto.request.VehicleDtoRequest;
import com.fcamara.minhaVaga.exception.ColorIdInvalidException;
import com.fcamara.minhaVaga.exception.InvalidUserException;
import com.fcamara.minhaVaga.exception.ModelIdInvalidException;
import com.fcamara.minhaVaga.exception.PlateAlredyRegisteredException;
import com.fcamara.minhaVaga.model.CarParkUsage;
import com.fcamara.minhaVaga.model.Color;
import com.fcamara.minhaVaga.model.Model;
import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.model.Vehicle;
import com.fcamara.minhaVaga.repository.CarParkUsageRepository;
import com.fcamara.minhaVaga.repository.ColorRepository;
import com.fcamara.minhaVaga.repository.ModelRepository;
import com.fcamara.minhaVaga.repository.UserRepository;
import com.fcamara.minhaVaga.repository.VehicleRepository;

@Service
public class VehicleService {

	private UserRepository userRepository;

	private VehicleRepository vehicleRepository;

	private ColorRepository colorRepository;

	private ModelRepository modelRepository;
	
	private CarParkUsageRepository carParkUsageRepository;

	@Autowired
	public VehicleService(UserRepository userRepository, VehicleRepository vehicleRepository,
			ColorRepository colorRepository, ModelRepository modelRepository, CarParkUsageRepository carParkUsageRepository) {
		super();
		this.userRepository = userRepository;
		this.vehicleRepository = vehicleRepository;
		this.colorRepository = colorRepository;
		this.modelRepository = modelRepository;
		this.carParkUsageRepository = carParkUsageRepository;
	}

	public Vehicle findOneVehicle(Long id) {
		Optional<Vehicle> searchedVehicle = vehicleRepository.findById(id);
		if (searchedVehicle.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id invalido.");
		return searchedVehicle.get();
	}

	public Page<Color> listAllColors(Pageable pageable) {
		return colorRepository.findAll(pageable);
	}

	public Page<Model> listAllModels(Pageable pageable) {
		return modelRepository.findAll(pageable);
	}

	public Vehicle registerVehicle(Long userId, @Valid VehicleDtoRequest vehicleRequest) {

		IfFindVehicleByPlateThrowsException(vehicleRequest.getPlate());

		User searchedUser = findUser(userId);
		Color searchedColor = findColor(vehicleRequest.getColorId());
		Model searchedModel = findModel(vehicleRequest.getModelId());

		Vehicle vehicleToRegister = vehicleRequest.convertToVehicle(searchedUser, searchedColor, searchedModel);

		return vehicleRepository.save(vehicleToRegister);

	}

	@Transactional
	public Vehicle changeVehicleColor(Long userId, Long vehicleId, Long colorId) {
		Vehicle vehicle = findVehicle(vehicleId, userId);
		Color color = findColor(colorId);
		vehicle.setColor(color);
		return vehicle;
	}

	@Transactional
	public void deleteVehicle(Long vehicleId, Long userId) {
		Vehicle vehicle = findVehicle(vehicleId, userId);
		checkIfCarIsParkedThrowsExceptionIfTrue(vehicleId);
		vehicle.setIsActive(false);
		return;
	}

	private User findUser(Long userId) {
		Optional<User> searchedUser = userRepository.findById(userId);
		if (searchedUser.isEmpty())
			throw new InvalidUserException("Id invalido.");
		return searchedUser.get();
	}

	private void IfFindVehicleByPlateThrowsException(String plate) {
		Optional<Vehicle> searchedVehicle = vehicleRepository.findByPlate(plate);
		if (searchedVehicle.isPresent()) {
			throw new PlateAlredyRegisteredException("Placa já registrada.");
		}
		;
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
			throw new ColorIdInvalidException("Id de cor invalido.");
		return searchedColor.get();
	}

	private Model findModel(Long modelId) {
		Optional<Model> searchedModel = modelRepository.findById(modelId);
		if (searchedModel.isEmpty())
			throw new ModelIdInvalidException("Modelo invalido.");
		return searchedModel.get();
	}

	private void checkIfCarIsParkedThrowsExceptionIfTrue(Long vehicleId) {
		Optional<CarParkUsage> vehicleParked = carParkUsageRepository.findByVehicleIdAndExitTimeIsNull(vehicleId);
		if(vehicleParked.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Veiculo ainda estacionado não pode ser apagado.");
	}
	
}
