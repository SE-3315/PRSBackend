package com.example.patientrecordsystem.dto;

import jakarta.validation.constraints.NotBlank;

public record DepartmentCreateRequest(@NotBlank String name, String description, Boolean isActive) {}
