package com.example.patientrecordsystem.dto;

/**
 * Authentication response containing an access token and token type.
 */
public record AuthResponse(String accessToken, String tokenType) {}
