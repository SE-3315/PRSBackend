package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.AuthResponse;
import com.example.patientrecordsystem.dto.LoginRequest;
import com.example.patientrecordsystem.dto.RegisterRequest;
import com.example.patientrecordsystem.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller that handles user registration and login.
 *
 * <p>Provides endpoints for user registration and authentication, returning
 * authentication responses (e.g., JWT tokens) where applicable.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new user account.
     *
     * @param request the registration request
     * @return an empty response with 200 status
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        authService.register(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param req the login request
     * @return an authentication response with the access token
     */
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }
}

