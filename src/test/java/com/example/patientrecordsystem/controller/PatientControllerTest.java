package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.PatientCreateRequest;
import com.example.patientrecordsystem.dto.PatientResponse;
import com.example.patientrecordsystem.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID patientId = UUID.randomUUID();
    private final UUID doctorId = UUID.randomUUID();
    private final UUID departmentId = UUID.randomUUID();

    @Test
    @WithMockUser
    void list_ShouldReturnListOfPatients() throws Exception {
        // Given
        List<PatientResponse> patients = List.of(
                new PatientResponse(patientId, "John", "Doe", "123456789", "Male", "1234567890", doctorId, departmentId)
        );
        when(patientService.list()).thenReturn(patients);

        // When & Then
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"));

        verify(patientService).list();
    }

    @Test
    @WithMockUser
    void get_ShouldReturnPatient() throws Exception {
        // Given
        PatientResponse response = new PatientResponse(patientId, "John", "Doe", "123456789", "Male", "1234567890", doctorId, departmentId);
        when(patientService.get(patientId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/patients/{id}", patientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(patientId.toString()))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(patientService).get(patientId);
    }

    @Test
    @WithMockUser
    void create_ShouldCreatePatient() throws Exception {
        // Given
        PatientCreateRequest request = new PatientCreateRequest(
                "John", "Doe", "123456789", LocalDate.of(1990, 1, 1), "Male", "1234567890",
                "john@example.com", "123 Main St", "O+", "None", "None", "Insurance Co", "INS123",
                "Jane Doe", "0987654321", doctorId, departmentId
        );
        PatientResponse response = new PatientResponse(patientId, "John", "Doe", "123456789", "Male", "1234567890", doctorId, departmentId);
        when(patientService.create(any(PatientCreateRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/patients")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(patientId.toString()));

        verify(patientService).create(any(PatientCreateRequest.class));
    }

    @Test
    @WithMockUser
    void update_ShouldUpdatePatient() throws Exception {
        // Given
        PatientCreateRequest request = new PatientCreateRequest(
                "John", "Smith", "123456789", LocalDate.of(1990, 1, 1), "Male", "1234567890",
                "john@example.com", "123 Main St", "O+", "None", "None", "Insurance Co", "INS123",
                "Jane Doe", "0987654321", doctorId, departmentId
        );
        PatientResponse response = new PatientResponse(patientId, "John", "Smith", "123456789", "Male", "1234567890", doctorId, departmentId);
        when(patientService.update(eq(patientId), any(PatientCreateRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(put("/api/patients/{id}", patientId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lastName").value("Smith"));

        verify(patientService).update(eq(patientId), any(PatientCreateRequest.class));
    }

    @Test
    @WithMockUser
    void delete_ShouldDeletePatient() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/patients/{id}", patientId)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(patientService).delete(patientId);
    }
}