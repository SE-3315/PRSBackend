package com.example.patientrecordsystem.dto;

import java.util.UUID;

public record DoctorResponse(
        UUID id,
        UUID userId,
        UUID departmentId,
        String licenseNumber,
        String specialization,
        Boolean isActive
) {}
