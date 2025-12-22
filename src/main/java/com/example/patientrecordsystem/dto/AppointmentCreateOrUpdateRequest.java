package com.example.patientrecordsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO used to create or update an appointment.
 *
 * <p>Contains the IDs for patient, doctor and department as well as appointment date,
 * status and reason for the appointment.
 */
public record AppointmentCreateOrUpdateRequest(
        @NotNull UUID patientId,
        @NotNull UUID doctorId,
        @NotNull UUID departmentId,
        @NotNull Instant appointmentDate,
        @NotBlank String status,
        String reason
) {}