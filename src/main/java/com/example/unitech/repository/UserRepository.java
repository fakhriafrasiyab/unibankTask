package com.example.unitech.repository;

import com.example.unitech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByPin(String pin);
    Boolean existsByPin(String pin);
}
