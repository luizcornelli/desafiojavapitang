package com.desafiojavapitang.services.mappers;

import com.desafiojavapitang.dto.CarResponse;
import com.desafiojavapitang.entities.CarEntity;
import org.springframework.stereotype.Component;

@Component
public class CarEntityToCarResponseMapper implements Mapper<CarEntity, CarResponse> {

    @Override
    public CarResponse map(CarEntity input) {

        return new CarResponse(input.getId(),
                input.getYear(),
                input.getLicensePlate(),
                input.getModel(),
                input.getColor());
    }
}
