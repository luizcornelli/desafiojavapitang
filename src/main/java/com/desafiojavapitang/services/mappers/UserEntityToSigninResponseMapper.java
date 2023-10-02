package com.desafiojavapitang.services.mappers;

import com.desafiojavapitang.dto.CarResponse;
import com.desafiojavapitang.dto.SigninResponse;
import com.desafiojavapitang.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserEntityToSigninResponseMapper implements Mapper<UserEntity, SigninResponse> {

    @Override
    public SigninResponse map(UserEntity input) {

        Date birthday = input.getBirthday();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthdayFormat = dateFormat.format(birthday);
        List<CarResponse> carResponseList = new ArrayList<>();

        input.getCars().forEach(car -> {
            CarResponse carResponse = new CarResponse();
            carResponse.setId(car.getId());
            carResponse.setYear(car.getYear());
            carResponse.setModel(car.getModel());
            carResponse.setLicensePlate(car.getLicensePlate());
            carResponse.setColor(car.getColor());

            carResponseList.add(carResponse);
        });

        SigninResponse signinResponse = new SigninResponse(input.getFirstName(), input.getLastName(),
                input.getEmail(), birthdayFormat, input.getPhone());

        signinResponse.getCars().addAll(carResponseList);
        return signinResponse;
    }

}
