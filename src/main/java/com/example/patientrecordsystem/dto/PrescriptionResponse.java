package com.example.patientrecordsystem.dto;

import java.time.Instant;
import java.util.UUID;

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