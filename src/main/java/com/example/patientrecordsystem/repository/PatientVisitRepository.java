package com.example.patientrecordsystem.repository;


import com.example.patientrecordsystem.entity.PatientVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientVisitRepository extends JpaRepository<PatientVisit, UUID> {}
