package com.api.services;

import com.api.dto.CarRequest;
import com.api.dto.CarResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {

    Page<CarResponse> findAllPaged(String token, Pageable pageable);

    CarResponse findById(String token, Long id);

    void delete(String token, Long id);

    CarResponse update(String token, Long id, CarRequest carRequest);

    CarResponse create(String token, CarRequest carRequest);

    boolean existsByLicensePlate(String licensePlate);

    void validateAtributtes(CarRequest carRequest);

    void deleteAll();
}
