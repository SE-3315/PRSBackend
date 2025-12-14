package com.example.patientrecordsystem.repository;



import com.example.patientrecordsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<Object> findByUsername(String username);
}
