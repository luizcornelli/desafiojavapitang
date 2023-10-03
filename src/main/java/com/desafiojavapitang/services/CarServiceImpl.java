package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.CarRequest;
import com.desafiojavapitang.dto.CarResponse;
import com.desafiojavapitang.dto.SigninResponse;
import com.desafiojavapitang.dto.UserResponse;
import com.desafiojavapitang.entities.CarEntity;
import com.desafiojavapitang.entities.UserEntity;
import com.desafiojavapitang.repositories.CarRepository;
import com.desafiojavapitang.services.mappers.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public CarResponse findById(String token, Long id) {

		userService.findAuthenticateUser(token);

		CarEntity carEntity = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Entity not found: " + id));

		return carEntityToCarResponseMapper.map(carEntity);
	}

	@Override
	public void delete(String token, Long id) {

		userService.findAuthenticateUser(token);

		repository.deleteById(id);
	}

	@Override
	public CarResponse update(String token, Long id, CarRequest carRequest) {

		userService.findAuthenticateUser(token);

		CarEntity carEntity = repository.getOne(id);
		carEntity.setYear(carRequest.getYear());
		carEntity.setLicensePlate(carRequest.getLicensePlate());
		carEntity.setModel(carRequest.getModel());
		carEntity.setColor(carRequest.getColor());

		return carEntityToCarResponseMapper.map(repository.save(carEntity));
	}
}
