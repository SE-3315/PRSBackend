package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.DoctorCreateRequest;
import com.example.patientrecordsystem.dto.DoctorResponse;
import com.example.patientrecordsystem.service.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID doctorId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final UUID departmentId = UUID.randomUUID();

    @Test
    @WithMockUser
    void create_ShouldCreateDoctor() throws Exception {
        // Given
        DoctorCreateRequest request = new DoctorCreateRequest(
                userId, departmentId, "LIC123", "Cardiology", "1234567890", "doc@example.com",
                "101", "9-5", "Experienced doctor", true
        );
        DoctorResponse response = new DoctorResponse(doctorId, userId, departmentId, "LIC123", "Cardiology", true);
        when(doctorService.create(any(DoctorCreateRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/doctors")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(doctorId.toString()))
                .andExpect(jsonPath("$.licenseNumber").value("LIC123"));

        verify(doctorService).create(any(DoctorCreateRequest.class));
    }

    @Test
    @WithMockUser
    void list_ShouldReturnListOfDoctors() throws Exception {
        // Given
        List<DoctorResponse> doctors = List.of(
                new DoctorResponse(doctorId, userId, departmentId, "LIC123", "Cardiology", true)
        );
        when(doctorService.list()).thenReturn(doctors);

        // When & Then
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].specialization").value("Cardiology"));

        verify(doctorService).list();
    }

    @Test
    @WithMockUser
    void delete_ShouldDeleteDoctor() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/doctors/{id}", doctorId)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(doctorService).delete(doctorId);
    }
}