package com.example.patientrecordsystem.dto;

import java.time.LocalDateTime;

/**
 * Error response DTO containing error details.
 */
public record ErrorResponse(
        String message,
        String details,
        LocalDateTime timestamp
) {
    public ErrorResponse(String message, String details) {
        this(message, details, LocalDateTime.now());
    }

    public ErrorResponse(String message) {
        this(message, null, LocalDateTime.now());
    }
}

