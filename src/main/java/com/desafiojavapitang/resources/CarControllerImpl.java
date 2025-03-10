package com.desafiojavapitang.resources;

import com.desafiojavapitang.dto.CarRequest;
import com.desafiojavapitang.dto.CarResponse;
import com.desafiojavapitang.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarControllerImpl implements CarController{

    @Autowired
    private CarService carService;

    @Override
    public ResponseEntity<Page<CarResponse>> findAllPaged(String token, Pageable pageable) {
        return ResponseEntity.ok(carService.findAllPaged(token, pageable));
    }

    @Override
    public ResponseEntity<CarResponse> findById(String token, Long id) {
        return ResponseEntity.ok(carService.findById(token, id));
    }

    @Override
    public ResponseEntity<Void> delete(String token, Long id) {

        carService.delete(token, id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CarResponse> update(String token, Long id, CarRequest carRequest) {
        return ResponseEntity.ok(carService.update(token, id, carRequest));
    }

    @Override
    public ResponseEntity<CarResponse> create(String token, CarRequest carRequest) {
        return ResponseEntity.ok(carService.create(token, carRequest));
    }

    @Override
    public ResponseEntity<Void> deleteAll() {
        carService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
