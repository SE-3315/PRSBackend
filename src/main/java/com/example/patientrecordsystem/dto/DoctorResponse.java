package com.example.patientrecordsystem.dto;

import java.util.UUID;

/**
 * Response DTO representing a doctor's public details.
 */
public record DoctorResponse(
        UUID id,
        UUID userId,
        UUID departmentId,
        String licenseNumber,
        String specialization,
        Boolean isActive
) {}
