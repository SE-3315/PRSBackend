package com.example.patientrecordsystem.dto;

import java.util.UUID;

public record DepartmentResponse(UUID id, String name, String description, Boolean isActive) {}
