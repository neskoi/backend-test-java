package com.fcamara.minhaVaga.service;

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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fcamara.minhaVaga.dto.request.VehicleDtoRequest;
import com.fcamara.minhaVaga.exception.ColorIdInvalidException;
import com.fcamara.minhaVaga.exception.InvalidUserException;
import com.fcamara.minhaVaga.exception.ModelIdInvalidException;
import com.fcamara.minhaVaga.exception.PlateAlredyRegisteredException;
import com.fcamara.minhaVaga.model.Color;
import com.fcamara.minhaVaga.model.Model;
import com.fcamara.minhaVaga.model.TypeOfVehicle;
import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.model.Vehicle;
import com.fcamara.minhaVaga.repository.CarParkUsageRepository;
import com.fcamara.minhaVaga.repository.ColorRepository;
import com.fcamara.minhaVaga.repository.ModelRepository;
import com.fcamara.minhaVaga.repository.UserRepository;
import com.fcamara.minhaVaga.repository.VehicleRepository;

public class VehicleServiceTest {
	
	@Mock
	private UserRepository userRepository;

	@Mock
	private VehicleRepository vehicleRepository;

	@Mock
	private ColorRepository colorRepository;

	@Mock
	private ModelRepository modelRepository;
	
	@Mock
	private CarParkUsageRepository carParkUsageRepository;
	
	private VehicleService vehicleService;
	

	@BeforeEach
	public void BeforeEach() {
		MockitoAnnotations.openMocks(this);
		this.vehicleService = new VehicleService(userRepository, vehicleRepository, colorRepository, modelRepository, carParkUsageRepository);
	}

	@Test
	public void shouldReturnOneVehicle() {
		Long searchedVehicle = 1l;
		Mockito.when(vehicleRepository.findById(anyLong())).thenReturn(repoVehicleFindByIdMockBehavior(searchedVehicle));
		Vehicle vehicle = vehicleService.findOneVehicle(searchedVehicle);
		
		Assertions.assertTrue(vehicle instanceof Vehicle);
	}
	
	@Test
	public void shouldThrowsExceptionIfInvalidVehicleId() {
		Long searchedVehicle = 3l;
		Mockito.when(vehicleRepository.findById(anyLong())).thenReturn(repoVehicleFindByIdMockBehavior(searchedVehicle));
		Assertions.assertThrows(ResponseStatusException.class, () -> vehicleService.findOneVehicle(searchedVehicle));
	}
	
	@Test
	public void shouldThrowsExceptionIfPlateIsAlreadyRegistered() {
		String searchedPlate = "AAA0000";
		Mockito.when(vehicleRepository.findByPlate(any())).thenReturn(repoVehicleFindByPlate(searchedPlate));
		VehicleDtoRequest vehicle = new VehicleDtoRequest();
		Assertions.assertThrows(PlateAlredyRegisteredException.class, () -> vehicleService.registerVehicle(null, vehicle));
	}
	
	@Test
	public void shouldThrowsExceptionIfUserIsInvalid() {
		String searchedPlate = "AAA2222";
		Mockito.when(vehicleRepository.findByPlate(any())).thenReturn(repoVehicleFindByPlate(searchedPlate));
		Mockito.when(userRepository.findById(any())).thenReturn(repoUserFindByIdMockBehavior(false));
		VehicleDtoRequest vehicle = new VehicleDtoRequest();
		Assertions.assertThrows(InvalidUserException.class, () -> vehicleService.registerVehicle(1l, vehicle));
	}
	
	@Test
	public void shouldThrowsExceptionIfColorIdIsInvalid() {
		String searchedPlate = "AAA2222";
		Long searchedColor = 13l;
		Mockito.when(vehicleRepository.findByPlate(any())).thenReturn(repoVehicleFindByPlate(searchedPlate));
		Mockito.when(userRepository.findById(any())).thenReturn(repoUserFindByIdMockBehavior(true));
		Mockito.when(colorRepository.findById(null)).thenReturn(repoColorFindByIdMockBehavior(searchedColor));		
		VehicleDtoRequest vehicle = new VehicleDtoRequest();
		Assertions.assertThrows(ColorIdInvalidException.class, () -> vehicleService.registerVehicle(1l, vehicle));
	}
	
	@Test
	public void shouldThrowsExceptionIfModelIdIsInvalid() {
		String searchedPlate = "AAA2222";
		Long searchedColor = 1l;
		Long searchedModel = 13l;

		Mockito.when(vehicleRepository.findByPlate(any())).thenReturn(repoVehicleFindByPlate(searchedPlate));
		Mockito.when(userRepository.findById(any())).thenReturn(repoUserFindByIdMockBehavior(true));
		Mockito.when(colorRepository.findById(null)).thenReturn(repoColorFindByIdMockBehavior(searchedColor));		
		Mockito.when(modelRepository.findById(null)).thenReturn(repoModelFindByIdMockBehavior(searchedModel));	
		VehicleDtoRequest vehicle = new VehicleDtoRequest();
		Assertions.assertThrows(ModelIdInvalidException.class, () -> vehicleService.registerVehicle(1l, vehicle));
	}
	
	@Test
	public void shouldRegisterVehicleAndReturnItself() {
		String searchedPlate = "AAA2222";
		Long searchedColor = 1l;
		Long searchedModel = 1l;

		Mockito.when(vehicleRepository.findByPlate(any())).thenReturn(repoVehicleFindByPlate(searchedPlate));
		Mockito.when(userRepository.findById(any())).thenReturn(repoUserFindByIdMockBehavior(true));
		Mockito.when(colorRepository.findById(null)).thenReturn(repoColorFindByIdMockBehavior(searchedColor));		
		Mockito.when(modelRepository.findById(null)).thenReturn(repoModelFindByIdMockBehavior(searchedModel));	
		Mockito.when(vehicleRepository.save(any())).thenReturn(repoVehicleSaveMockBehavior());
		VehicleDtoRequest vehicle = new VehicleDtoRequest();
		Vehicle registeredVehicle = vehicleService.registerVehicle(1l, vehicle);
		
		Assertions.assertTrue(registeredVehicle instanceof Vehicle);
		verify(vehicleRepository, times(1)).save(any());
	}
	
	
	@Test
	public void shouldChangeVehicleColor() {
		Long searchedVehicle = 1l;
		Long newColor = 2l;
		
		Mockito.when(vehicleRepository.findByIdAndUserId(any(), any())).thenReturn(repoVehicleFindByIdMockBehavior(searchedVehicle));
		Mockito.when(colorRepository.findById(any())).thenReturn(repoColorFindByIdMockBehavior(newColor));		
		
		Vehicle vehicle = vehicleService.changeVehicleColor(null, searchedVehicle, newColor);
		Assertions.assertTrue(vehicle.getId() != newColor);
	}
		
	private Vehicle repoVehicleSaveMockBehavior() {
		return new Vehicle();
	}
	
	private Optional<Model> repoModelFindByIdMockBehavior(Long modelId) {
		List<Model> models = fakeModelDB();
		for(Model m : models) {
			if(m.getId() == modelId) 
				return Optional.of(m);
		}
		return Optional.empty();
	}

	private Optional<Color> repoColorFindByIdMockBehavior(Long colorId) {
		List<Color> colors = fakeColorDB();
		for(Color c : colors) {
			if(c.getId() == colorId) 
				return Optional.of(c);
		}
		return Optional.empty();
	}

	private Optional<User> repoUserFindByIdMockBehavior(boolean isPresent){
		if(isPresent)
			return Optional.of(new User());
		return Optional.empty();
	}
	
	private Optional<Vehicle> repoVehicleFindByPlate(String plate){
		List<Vehicle> vehicles = fakeVehicleDB();
		
		for(Vehicle v : vehicles) {
			if(v.getPlate() == plate)
				return Optional.of(v);
		}
		
		return Optional.empty();
	}
	
	private Optional<Vehicle> repoVehicleFindByIdMockBehavior(Long vehicleId){
		List<Vehicle> vehicles = fakeVehicleDB();
		
		for(Vehicle v : vehicles) {
			if(v.getId() == vehicleId)
				return Optional.of(v);
		}
		
		return Optional.empty();
	}
	
	private List<Vehicle> fakeVehicleDB(){
		List<Vehicle> vehicles = new ArrayList<>();
		
		List<Color> colors = fakeColorDB();
		List<Model> models = fakeModelDB();
		
		Vehicle v1 = new Vehicle(null, models.get(0), colors.get(0), "AAA0000");
		v1.setId(1l);
		Vehicle v2 = new Vehicle(null, models.get(1), colors.get(1), "BBB0000");
		v2.setId(2l);
		
		vehicles.add(v1);
		vehicles.add(v2);
		return vehicles;
	}
	
	private List<Color> fakeColorDB(){
		List<Color> colors = new ArrayList<>();
		Color c1 = new Color("Preto");
		c1.setId(1l);
		Color c2 = new Color("Branco");
		c2.setId(2l);
		colors.add(c1);
		colors.add(c2);
		return colors;
	}
	
	private List<Model> fakeModelDB(){
		List<Model> models = new ArrayList<>();
		Model m1 = new Model("Lego - 78", null, TypeOfVehicle.MOTO);
		m1.setId(1l);
		Model m2 = new Model("Vrum", null, TypeOfVehicle.CARRO);
		m2.setId(2l);
		
		models.add(m1);
		models.add(m2);
		return models;
	}
}




