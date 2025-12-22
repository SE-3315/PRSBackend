package com.example.patientrecordsystem.dto;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO used to create a new patient record.
 */
public record PatientCreateRequest(
        String firstName,
        String lastName,
        String nationalId,
        LocalDate birthDate,
        String gender,
        String phone,
        String email,
        String address,
        String bloodType,
        String allergies,
        String chronicConditions,
        String insuranceProvider,
        String insuranceNumber,
        String emergencyContactName,
        String emergencyContactPhone,
        UUID primaryDoctorId,
        UUID departmentId
) {}
