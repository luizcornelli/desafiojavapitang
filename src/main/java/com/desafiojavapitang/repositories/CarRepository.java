package com.desafiojavapitang.repositories;


import com.desafiojavapitang.entities.CarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

    @Query(value = "SELECT * FROM tb_car WHERE user_id = :id", nativeQuery = true)
    List<CarEntity> finByUserId(@Param("id") Long id);

    boolean existsByLicensePlate(String licensePlate);
}
