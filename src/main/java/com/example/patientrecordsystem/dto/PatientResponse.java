package com.example.patientrecordsystem.dto;

import java.util.UUID;

public record PatientResponse(
        UUID id,
        String firstName,
        String lastName,
        String nationalId,
        String gender,
        String phone,
        UUID doctorId,
        UUID departmentId
) {}
