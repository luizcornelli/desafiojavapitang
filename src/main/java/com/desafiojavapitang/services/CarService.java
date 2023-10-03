package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarService {

    List<CarResponse> findAllPaged(String token);
}
