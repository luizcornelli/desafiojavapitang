package com.api.services.mappers;

import com.api.dto.UserResponse;
import com.api.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserEntityToUserResponseMapper implements Mapper<UserEntity, UserResponse> {

    @Override
    public UserResponse map(UserEntity input) {

        Date birthday = input.getBirthday();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthdayFormat = dateFormat.format(birthday);

        String lastLogin = input.getLastLogin() != null ? input.getLastLogin().toString() : null;

        return new UserResponse(input.getId(),
                input.getFirstName(),
                input.getLastName(),
                input.getEmail(),
                birthdayFormat,
                input.getPhone(),
                input.getCreatedAt().toString(),
                lastLogin);
    }
}
