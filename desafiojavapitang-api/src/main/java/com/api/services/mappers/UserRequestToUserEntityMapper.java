package com.api.services.mappers;

import com.api.dto.UserRequest;
import com.api.entities.CarEntity;
import com.api.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRequestToUserEntityMapper implements Mapper<UserRequest, UserEntity> {

    @Override
    public UserEntity map(UserRequest input) {

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(input.getFirstName());
        userEntity.setLastName(input.getLastName());
        userEntity.setEmail(input.getEmail());
        userEntity.setBirthday(input.getBirthday());
        userEntity.setLogin(input.getLogin());
        userEntity.setPassword(input.getPassword());
        userEntity.setPhone(input.getPhone());

        List<CarEntity> carEntityList = new ArrayList<>();

        input.getCars().forEach(car -> {

            CarEntity carEntity = new CarEntity();
            carEntity.setYear(car.getYear());
            carEntity.setLicensePlate(car.getLicensePlate());
            carEntity.setModel(car.getModel());
            carEntity.setColor(car.getColor());
            carEntity.setUser(userEntity);

            carEntityList.add(carEntity);
        });

       userEntity.getCars().addAll(carEntityList);

        return userEntity;
    }
}
