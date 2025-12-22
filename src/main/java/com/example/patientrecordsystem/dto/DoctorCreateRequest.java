package com.example.patientrecordsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * DTO used to create a doctor record.
 */
public record DoctorCreateRequest(
        @NotNull UUID userId,
        @NotNull UUID departmentId,
        @NotBlank String licenseNumber,
        String specialization,
        String phone,
        String email,
        String roomNumber,
        String workingHours,
        String biography,
        Boolean isActive
) {}
