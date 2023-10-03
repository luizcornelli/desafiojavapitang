package com.desafiojavapitang.resources;

import com.desafiojavapitang.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CarController {

    @GetMapping(value = "/api/cars")
    ResponseEntity<List<CarResponse>> findAllPaged(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/cars/{id}")
    ResponseEntity<CarResponse> findById(@RequestHeader("Authorization") String token,
                                                   @PathVariable Long id);

    @DeleteMapping(value = "/api/cars/{id}")
    ResponseEntity<Void> delete(@RequestHeader("Authorization") String token,
                                         @PathVariable Long id);

    @PutMapping(value = "/api/cars/{id}")
    ResponseEntity<CarResponse> update(@RequestHeader("Authorization") String token,
                                @PathVariable Long id,
                                @RequestBody CarRequest carRequest);
}
