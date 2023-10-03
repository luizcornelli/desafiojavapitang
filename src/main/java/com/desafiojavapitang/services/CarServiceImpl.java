package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.CarResponse;
import com.desafiojavapitang.dto.UserResponse;
import com.desafiojavapitang.entities.CarEntity;
import com.desafiojavapitang.repositories.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
	
	private Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);
	
	@Autowired
	private CarRepository repository;

	@Autowired
	private UserService userService;

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
}
