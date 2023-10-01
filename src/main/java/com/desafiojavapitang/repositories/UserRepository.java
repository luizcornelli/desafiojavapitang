package com.desafiojavapitang.repositories;


import com.desafiojavapitang.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByLogin(String login);

    UserEntity findByEmail(String username);
}
