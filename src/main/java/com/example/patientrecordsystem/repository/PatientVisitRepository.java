package com.example.patientrecordsystem.repository;


import com.example.patientrecordsystem.entity.PatientVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Spring Data JPA repository for {@link com.example.patientrecordsystem.entity.PatientVisit} entities.
 */
public interface PatientVisitRepository extends JpaRepository<PatientVisit, UUID> {
    public void deleteByPatient_Id(UUID id);

}