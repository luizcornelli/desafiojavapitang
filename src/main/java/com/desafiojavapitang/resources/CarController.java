package com.desafiojavapitang.resources;

import com.desafiojavapitang.dto.CarRequest;
import com.desafiojavapitang.dto.CarResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "cars")
public interface CarController {

    @Operation(summary = "Consulta todos os carros do usuário logado", method = "GET")
    @GetMapping(value = "/api/cars")
    ResponseEntity<Page<CarResponse>> findAllPaged(@RequestHeader("Authorization") String token,
                                                   Pageable pageable);

    @Operation(summary = "Consulta um carro do usuário logado", method = "GET")
    @GetMapping(value = "/api/cars/{id}")
    ResponseEntity<CarResponse> findById(@RequestHeader("Authorization") String token,
                                                   @PathVariable Long id);

    @Operation(summary = "Remove um carro do usuário logado", method = "DELETE")
    @DeleteMapping(value = "/api/cars/{id}")
    ResponseEntity<Void> delete(@RequestHeader("Authorization") String token,
                                         @PathVariable Long id);

    @Operation(summary = "Modifica um carro do usuário logado", method = "PUT")
    @PutMapping(value = "/api/cars/{id}")
    ResponseEntity<CarResponse> update(@RequestHeader("Authorization") String token,
                                       @PathVariable Long id,
                                       @RequestBody CarRequest carRequest);

    @Operation(summary = "Registra um carro do usuário logado", method = "POST")
    @PostMapping(value = "/api/cars")
    ResponseEntity<CarResponse> create(@RequestHeader("Authorization") String token,
                                       @RequestBody CarRequest carRequest);

    @Operation(summary = "Limpa tabela de carros", method = "DELETE")
    @DeleteMapping(value = "/api/cars/deleteAll")
    ResponseEntity<Void> deleteAll();
}
