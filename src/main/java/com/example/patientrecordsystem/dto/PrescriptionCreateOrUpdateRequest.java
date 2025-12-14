package com.example.patientrecordsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

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