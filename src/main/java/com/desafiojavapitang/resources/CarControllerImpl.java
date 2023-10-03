package com.desafiojavapitang.resources;

import com.desafiojavapitang.dto.CarResponse;
import com.desafiojavapitang.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarControllerImpl implements CarController{

    @Autowired
    private CarService carService;

    @Override
    public ResponseEntity<List<CarResponse>> findAllPaged(String token) {
        return ResponseEntity.ok(carService.findAllPaged(token));
    }
}
