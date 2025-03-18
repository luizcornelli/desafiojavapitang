package com.api.services.mappers;

import com.api.dto.CarResponse;
import com.api.entities.CarEntity;
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
