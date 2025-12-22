package com.example.patientrecordsystem.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Registration request payload for creating a new user account.
 */
public record RegisterRequest(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String role,
        String firstName,
        String lastName,
        String phone
) {}
