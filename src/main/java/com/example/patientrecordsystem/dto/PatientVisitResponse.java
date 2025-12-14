package com.example.patientrecordsystem.dto;

import java.time.Instant;
import java.util.UUID;

public record PatientVisitResponse(
        UUID id,
        UUID patientId,
        UUID doctorId,
        Instant visitDate,
        String symptoms,
        String diagnosis,
        String treatmentPlan,
        String notes
) {}