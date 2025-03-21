package com.api.repositories;


import com.api.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);

    UserEntity findByEmail(String username);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}
