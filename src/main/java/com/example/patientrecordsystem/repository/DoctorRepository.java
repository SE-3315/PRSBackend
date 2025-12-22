package com.example.patientrecordsystem.repository;

import com.example.patientrecordsystem.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Spring Data JPA repository for {@link com.example.patientrecordsystem.entity.Doctor} entities.
 */
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    boolean existsByLicenseNumber(String licenseNumber);
}
