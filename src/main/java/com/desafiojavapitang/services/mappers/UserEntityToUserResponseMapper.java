package com.desafiojavapitang.services.mappers;

import com.desafiojavapitang.dto.CarResponse;
import com.desafiojavapitang.dto.UserResponse;
import com.desafiojavapitang.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserEntityToUserResponseMapper implements Mapper<UserEntity, UserResponse> {

    @Override
    public UserResponse map(UserEntity input) {

        Date birthday = input.getBirthday();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthdayFormat = dateFormat.format(birthday);

        UserResponse userResponse = new UserResponse(input.getId(), input.getFirstName(),
                input.getLastName(), input.getEmail(), birthdayFormat,
                input.getPhone());

        List<CarResponse> carResponseList = new ArrayList<>();

        input.getCars().forEach(car ->{

            CarResponse carResponse = new CarResponse(car.getId(), car.getYear(), car.getLicensePlate(),
                    car.getModel(), car.getColor());
            carResponseList.add(carResponse);
        });

       userResponse.getCars().addAll(carResponseList);
       return userResponse;
    }

}
