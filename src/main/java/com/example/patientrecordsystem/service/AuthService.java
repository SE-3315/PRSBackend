package com.example.patientrecordsystem.service;

import com.example.patientrecordsystem.dto.AuthResponse;
import com.example.patientrecordsystem.dto.LoginRequest;
import com.example.patientrecordsystem.dto.RegisterRequest;
import com.example.patientrecordsystem.entity.User;
import com.example.patientrecordsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

/**
 * Service responsible for user registration and authentication.
 *
 * <p>Creates user accounts and issues JWT tokens on successful login.
 */
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

    /**
     * Registers a new user account.
     *
     * @param req the registration request containing email, password, name and role
     * @return the created user
     * @throws IllegalArgumentException if email already exists
     */
    @Transactional
    public User register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already exists");
        }
        String hash = passwordEncoder.encode(req.password());
        User user = new User(req.email(), hash, req.firstName(), req.lastName(), req.role(), req.phone());
        return userRepository.save(user);
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param req the login request containing email and password
     * @return an authentication response with access token and token type
     * @throws NotFoundException if email is not found or password is invalid
     */
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new NotFoundException("Invalid email or password"));
        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new NotFoundException("Invalid email or password");
        }
        String token = jwtService.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token, "Bearer");
    }

    @Transactional(readOnly = true)
    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}