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

        return new SigninResponse(input.getId(),
                input.getFirstName(),
                input.getLastName(),
                input.getEmail(),
                birthdayFormat,
                input.getPhone(),
                input.getLastLogin().toString(),
                input.getCreatedAt().toString());
    }
}
