package com.api.services;

import com.api.dto.CarRequest;
import com.api.dto.CarResponse;
import com.api.dto.UserResponse;
import com.api.entities.CarEntity;
import com.api.entities.UserEntity;
import com.api.repositories.CarRepository;
import com.api.services.exceptions.LicensePlateAlreadyExistsException;
import com.api.services.exceptions.MissingFieldsException;
import com.api.services.mappers.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarServiceImpl implements CarService {
	
	private Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);
	
	@Autowired
	private CarRepository repository;
//
//	@Autowired
//	private UserService userService;

	@Autowired
	private Mapper<CarEntity, CarResponse> carEntityToCarResponseMapper;

	@Override
	@Transactional(readOnly = true)
	public Page<CarResponse> findAllPaged(String token, Pageable pageable) {

		//UserResponse userResponse = userService.findAuthenticateUser(token);

		Page<CarEntity> carEntities = null; //repository.findByUserId(userResponse.getId(), pageable);

		return carEntities.map(carEntityToCarResponseMapper::map);
	}

	@Override
	@Transactional(readOnly = true)
	public CarResponse findById(String token, Long id) {

	//	userService.findAuthenticateUser(token);

		CarEntity carEntity = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Entity not found: " + id));

		return carEntityToCarResponseMapper.map(carEntity);
	}

	@Override
	@Transactional
	public void delete(String token, Long id) {

		//userService.findAuthenticateUser(token);

		repository.deleteById(id);
	}

	@Override
	@Transactional
	public CarResponse update(String token, Long id, CarRequest carRequest) {

		//UserResponse userResponse = userService.findAuthenticateUser(token);

		validateAtributtes(carRequest);

		CarEntity carEntity = repository.getOne(id);

//		if(!carEntity.getUser().getId().equals(userResponse.getId())) {
//			throw new RuntimeException("This car does not belong to the logged in user");
//		}

		carEntity.setYear(carRequest.getYear());
		carEntity.setLicensePlate(carRequest.getLicensePlate());
		carEntity.setModel(carRequest.getModel());
		carEntity.setColor(carRequest.getColor());

		return carEntityToCarResponseMapper.map(repository.save(carEntity));
	}

	@Override
	@Transactional
	public CarResponse create(String token, CarRequest carRequest) {

		//UserResponse userResponse = userService.findAuthenticateUser(token);

		validateAtributtes(carRequest);

		CarEntity carEntity = new CarEntity();
		carEntity.setYear(carRequest.getYear());
		carEntity.setLicensePlate(carRequest.getLicensePlate());
		carEntity.setModel(carRequest.getModel());
		carEntity.setColor(carRequest.getColor());

		UserEntity userEntity = new UserEntity();
		//userEntity.setId(userResponse.getId());

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

		if( year <= 0 || (licensePlate == null || licensePlate.isEmpty())
				|| (model == null || model.isEmpty())
				|| (color == null || color.isEmpty()) ){
			throw new MissingFieldsException("Missing fields");
		}
	}

	@Override
	@Transactional
	public void deleteAll() {
		repository.deleteAll();
	}
}
