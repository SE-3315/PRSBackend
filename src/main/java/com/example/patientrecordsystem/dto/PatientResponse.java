package com.example.patientrecordsystem.dto;

import java.util.UUID;

/**
 * Response DTO containing patient details.
 */
public record PatientResponse(
        UUID id,
        String firstName,
        String lastName,
        String nationalId,
        String gender,
        String phone,
        UUID doctorId,
        UUID departmentId
) {}
