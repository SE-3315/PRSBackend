package com.example.patientrecordsystem.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO to create a new department.
 */
public record DepartmentCreateRequest(@NotBlank String name, String description, Boolean isActive) {}
