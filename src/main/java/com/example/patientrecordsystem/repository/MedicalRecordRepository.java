package com.example.patientrecordsystem.repository;

import com.example.patientrecordsystem.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Spring Data JPA repository for {@link com.example.patientrecordsystem.entity.MedicalRecord} entities.
 */
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {
    public void deleteByPatient_Id(UUID patientID);
}
