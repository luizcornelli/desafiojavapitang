package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CarService {

    Page<CarResponse> findAllPaged(String token, Pageable pageable);

    CarResponse findById(String token, Long id);

    void delete(String token, Long id);

    CarResponse update(String token, Long id, CarRequest carRequest);

    CarResponse create(String token, CarRequest carRequest);

    boolean existsByLicensePlate(String licensePlate);

    void validateAtributtes(CarRequest carRequest);
}
