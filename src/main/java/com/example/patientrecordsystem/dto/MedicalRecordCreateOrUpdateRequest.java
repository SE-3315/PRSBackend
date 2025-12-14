package com.example.patientrecordsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MedicalRecordCreateOrUpdateRequest(
        @NotNull UUID patientId,
        @NotNull UUID doctorId,
        @NotBlank String recordType,
        String description,
        String attachments
) {}