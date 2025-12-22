package com.example.patientrecordsystem.config;

import com.example.patientrecordsystem.entity.User;
import com.example.patientrecordsystem.repository.UserRepository;
import com.example.patientrecordsystem.service.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;


import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

/**
 * Servlet filter that validates JWT tokens provided in the HTTP `Authorization` header
 * and sets the Spring Security context accordingly.
 *
 * <p>The filter extracts the bearer token, resolves the user email from the token using
 * {@link com.example.patientrecordsystem.service.JwtService} and loads user details from
 * {@link com.example.patientrecordsystem.repository.UserRepository}.
 */
public class JwtAuthFilter extends GenericFilter {

    private final JwtService jwtService;

    private final UserRepository userRepository;


    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        String authHeader = httpReq.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtService.extractEmail(token);
            Optional<User> userDetails = userRepository.findByEmail(email);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        chain.doFilter(request, response);
    }
}
