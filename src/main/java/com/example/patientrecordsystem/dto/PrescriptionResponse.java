package com.example.patientrecordsystem.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Response DTO representing a prescription.
 */
public record PrescriptionResponse(
        UUID id,
        UUID patientId,
        UUID doctorId,
        String medicationName,
        String dosage,
        String frequency,
        String duration,
        String instructions,
        Instant issuedAt
) {}