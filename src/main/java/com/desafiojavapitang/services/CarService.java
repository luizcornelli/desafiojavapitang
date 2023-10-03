package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.*;

import java.util.List;

public interface CarService {

    List<CarResponse> findAllPaged(String token);

    CarResponse findById(String token, Long id);

    void delete(String token, Long id);

    CarResponse update(String token, Long id, CarRequest carRequest);

    CarResponse create(String token, CarRequest carRequest);

    boolean existsByLicensePlate(String licensePlate);

    void validateAtributtes(CarRequest carRequest);
}
