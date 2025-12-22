package com.example.patientrecordsystem.dto;

import java.util.UUID;

/**
 * Response DTO for department information.
 */
public record DepartmentResponse(UUID id, String name, String description, Boolean isActive) {}
