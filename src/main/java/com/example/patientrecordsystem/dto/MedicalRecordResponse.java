package com.example.patientrecordsystem.dto;

import java.util.UUID;

public record MedicalRecordResponse(
        UUID id,
        UUID patientId,
        UUID doctorId,
        String recordType,
        String description,
        String attachments
) {}