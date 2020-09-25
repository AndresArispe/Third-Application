package com.milankas.training.userapi.persistance.repository;

import com.milankas.training.userapi.persistance.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PasswordRepository extends JpaRepository <Password, UUID> {
}
