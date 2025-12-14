package com.example.patientrecordsystem.repository;

import com.example.patientrecordsystem.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {}
