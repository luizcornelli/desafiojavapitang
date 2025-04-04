package com.api.services.mappers;

import com.api.dto.SigninResponse;
import com.api.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

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
