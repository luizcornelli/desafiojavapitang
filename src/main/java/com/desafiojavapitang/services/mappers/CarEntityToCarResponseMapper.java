package com.desafiojavapitang.services.mappers;

import com.desafiojavapitang.dto.CarResponse;
import com.desafiojavapitang.dto.SigninResponse;
import com.desafiojavapitang.entities.CarEntity;
import com.desafiojavapitang.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CarEntityToCarResponseMapper implements Mapper<CarEntity, CarResponse> {

    @Override
    public CarResponse map(CarEntity input) {

        CarResponse carResponse = new CarResponse(input.getId(), input.getYear(),
                input.getLicensePlate(), input.getModel(), input.getColor(),
                input.getUser().getId());

        return carResponse;
    }
}
