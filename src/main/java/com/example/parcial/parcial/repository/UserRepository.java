package com.example.parcial.parcial.repository;

import com.example.parcial.parcial.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	boolean existsByEmail(String email);
}
