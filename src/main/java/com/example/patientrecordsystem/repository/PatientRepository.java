package com.example.patientrecordsystem.repository;

import com.example.patientrecordsystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByNationalId(String nationalId);
    boolean existsByNationalId(String nationalId);
}
