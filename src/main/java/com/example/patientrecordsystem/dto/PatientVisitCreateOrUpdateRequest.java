package com.example.patientrecordsystem.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record PatientVisitCreateOrUpdateRequest(
        @NotNull UUID patientId,
        @NotNull UUID doctorId,
        @NotNull Instant visitDate,
        String symptoms,
        String diagnosis,
        String treatmentPlan,
        String notes
) {}