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
}
