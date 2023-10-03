package com.desafiojavapitang.services.mappers;

import com.desafiojavapitang.dto.CarResponse;
import com.desafiojavapitang.dto.UserResponseCreate;
import com.desafiojavapitang.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserEntityToUserResponseCreateMapper implements Mapper<UserEntity, UserResponseCreate> {

    @Override
    public UserResponseCreate map(UserEntity input) {

        Date birthday = input.getBirthday();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthdayFormat = dateFormat.format(birthday);

        String lastLogin = input.getLastLogin() != null ? input.getLastLogin().toString() : null;

        UserResponseCreate userResponseCreate = new UserResponseCreate(input.getId(),
                input.getFirstName(),
                input.getLastName(),
                input.getEmail(),
                birthdayFormat,
                input.getPhone(),
                input.getCreatedAt().toString(),
                lastLogin);

        List<CarResponse> carResponseList = new ArrayList<>();

        input.getCars().forEach(car ->{

            CarResponse carResponse = new CarResponse(car.getId(),
                    car.getYear(),
                    car.getLicensePlate(),
                    car.getModel(),
                    car.getColor());
            carResponseList.add(carResponse);
        });

        userResponseCreate.getCars().addAll(carResponseList);
        return userResponseCreate;
    }
}
