package com.example.patientrecordsystem.dto;

import java.time.Instant;
import java.util.UUID;

public record AppointmentResponse(
        UUID id,
        UUID patientId,
        UUID doctorId,
        UUID departmentId,
        Instant appointmentDate,
        String status,
        String reason
) {}