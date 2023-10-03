package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.CarRequest;
import com.desafiojavapitang.dto.CarResponse;
import com.desafiojavapitang.dto.UserResponse;
import com.desafiojavapitang.entities.CarEntity;
import com.desafiojavapitang.entities.UserEntity;
import com.desafiojavapitang.repositories.CarRepository;
import com.desafiojavapitang.services.exceptions.LicensePlateAlreadyExistsException;
import com.desafiojavapitang.services.exceptions.MissingFieldsException;
import com.desafiojavapitang.services.mappers.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
	
	private Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);
	
	@Autowired
	private CarRepository repository;

	@Autowired
	private UserService userService;

	@Autowired
	private Mapper<CarEntity, CarResponse> carEntityToCarResponseMapper;

	@Override
	@Transactional(readOnly = true)
	public List<CarResponse> findAllPaged(String token) {

		UserResponse userResponse = userService.findAuthenticateUser(token);

		List<CarEntity> carEntities = repository.finByUserId(userResponse.getId());
		List<CarResponse> carResponseList = new ArrayList<>();

		carEntities.forEach(car -> {

			CarResponse carResponse = new CarResponse();

			carResponse.setId(car.getId());
			carResponse.setYear(car.getYear());
			carResponse.setLicensePlate(car.getLicensePlate());
			carResponse.setModel(car.getModel());
			carResponse.setColor(car.getColor());

			carResponseList.add(carResponse);
		});

		return carResponseList;
	}

	@Override
	@Transactional(readOnly = true)
	public CarResponse findById(String token, Long id) {

		userService.findAuthenticateUser(token);

		CarEntity carEntity = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Entity not found: " + id));

		return carEntityToCarResponseMapper.map(carEntity);
	}

	@Override
	@Transactional
	public void delete(String token, Long id) {

		userService.findAuthenticateUser(token);

		repository.deleteById(id);
	}

	@Override
	@Transactional
	public CarResponse update(String token, Long id, CarRequest carRequest) {

		UserResponse userResponse = userService.findAuthenticateUser(token);

		validateAtributtes(carRequest);

		CarEntity carEntity = repository.getOne(id);

		if(!carEntity.getUser().getId().equals(userResponse.getId())) {
			throw new RuntimeException("This car does not belong to the logged in user");
		}

		carEntity.setYear(carRequest.getYear());
		carEntity.setLicensePlate(carRequest.getLicensePlate());
		carEntity.setModel(carRequest.getModel());
		carEntity.setColor(carRequest.getColor());

		return carEntityToCarResponseMapper.map(repository.save(carEntity));
	}

	@Override
	@Transactional
	public CarResponse create(String token, CarRequest carRequest) {

		UserResponse userResponse = userService.findAuthenticateUser(token);

		validateAtributtes(carRequest);

		CarEntity carEntity = new CarEntity();
		carEntity.setYear(carRequest.getYear());
		carEntity.setLicensePlate(carRequest.getLicensePlate());
		carEntity.setModel(carRequest.getModel());
		carEntity.setColor(carRequest.getColor());

		UserEntity userEntity = new UserEntity();
		userEntity.setId(userResponse.getId());

		carEntity.setUser(userEntity);

		return carEntityToCarResponseMapper.map(repository.save(carEntity));
	}

	@Override
	public boolean existsByLicensePlate(String licensePlate){
		return repository.existsByLicensePlate(licensePlate);
	}

	@Override
	public void validateAtributtes(CarRequest carRequest) {

		boolean existsLicensePlate = existsByLicensePlate(carRequest.getLicensePlate());
		if(existsLicensePlate){
			throw  new LicensePlateAlreadyExistsException("License plate already exists");
		}

		int year = carRequest.getYear();
		String licensePlate = carRequest.getLicensePlate();
		String model = carRequest.getModel();
		String color = carRequest.getColor();

		if( year <= 0 || (licensePlate == null || licensePlate.isEmpty() || licensePlate.isBlank())
				|| (model == null || model.isEmpty() || model.isBlank())
				|| (color == null || color.isEmpty() || color.isBlank()) ){
			throw new MissingFieldsException("Missing fields");
		}
	}
}
