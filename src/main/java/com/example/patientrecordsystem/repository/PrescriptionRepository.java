package com.example.patientrecordsystem.repository;


import com.example.patientrecordsystem.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Spring Data JPA repository for {@link com.example.patientrecordsystem.entity.Prescription} entities.
 */
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {}
