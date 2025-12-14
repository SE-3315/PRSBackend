package com.example.patientrecordsystem.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PatientUpdateRequest(
        String firstName,
        String lastName,
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
