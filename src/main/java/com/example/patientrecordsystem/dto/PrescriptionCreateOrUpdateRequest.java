package com.example.patientrecordsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO used to create or update a prescription for a patient.
 */
public record PrescriptionCreateOrUpdateRequest(
        @NotNull UUID patientId,
        @NotNull UUID doctorId,
        @NotBlank String medicationName,
        String dosage,
        String frequency,
        String duration,
        String instructions,
        Instant issuedAt
) {}