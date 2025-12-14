package com.example.patientrecordsystem.repository;


import com.example.patientrecordsystem.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {}
