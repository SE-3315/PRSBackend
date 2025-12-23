package com.example.patientrecordsystem.dto;

import java.util.UUID;

/**
 * Response DTO representing a medical record.
 */
public record MedicalRecordResponse(
        UUID id,
        UUID patientId,
        UUID doctorId,
        String recordType,
        String description,
        String attachments
) {}