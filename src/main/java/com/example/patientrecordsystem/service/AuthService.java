package com.example.patientrecordsystem.service;

import com.example.patientrecordsystem.dto.AuthResponse;
import com.example.patientrecordsystem.dto.LoginRequest;
import com.example.patientrecordsystem.dto.RegisterRequest;
import com.example.patientrecordsystem.entity.User;
import com.example.patientrecordsystem.repository.UserRepository;
import com.example.patientrecordsystem.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public void register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already exists");
        }
        String hash = passwordEncoder.encode(req.password());
        User user = new User(req.email(), hash, req.firstName(), req.lastName(), req.role(), req.phone());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new NotFoundException("Invalid email or password"));
        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new NotFoundException("Invalid email or password");
        }
        String token = jwtService.generateAccessToken(user.getEmail(), user.getRole());
        return new AuthResponse(token, "Bearer");
    }
}