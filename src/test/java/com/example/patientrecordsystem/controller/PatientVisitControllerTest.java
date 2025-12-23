package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.PatientVisitCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.PatientVisitResponse;
import com.example.patientrecordsystem.service.PatientVisitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientVisitController.class)
class PatientVisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientVisitService visitService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID visitId = UUID.randomUUID();
    private final UUID patientId = UUID.randomUUID();
    private final UUID doctorId = UUID.randomUUID();
    private final Instant visitDate = Instant.now();

    @Test
    @WithMockUser
    void list_ShouldReturnListOfPatientVisits() throws Exception {
        // Given
        List<PatientVisitResponse> visits = List.of(
                new PatientVisitResponse(visitId, patientId, doctorId, visitDate, "Cough", "Cold", "Rest", "Notes")
        );
        when(visitService.list()).thenReturn(visits);

        // When & Then
        mockMvc.perform(get("/api/visits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].symptoms").value("Cough"));

        verify(visitService).list();
    }

    @Test
    @WithMockUser
    void get_ShouldReturnPatientVisit() throws Exception {
        // Given
        PatientVisitResponse response = new PatientVisitResponse(visitId, patientId, doctorId, visitDate, "Cough", "Cold", "Rest", "Notes");
        when(visitService.get(visitId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/visits/{id}", visitId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(visitId.toString()))
                .andExpect(jsonPath("$.diagnosis").value("Cold"));

        verify(visitService).get(visitId);
    }

    @Test
    @WithMockUser
    void create_ShouldCreatePatientVisit() throws Exception {
        // Given
        PatientVisitCreateOrUpdateRequest request = new PatientVisitCreateOrUpdateRequest(
                patientId, doctorId, visitDate, "Cough", "Cold", "Rest", "Notes"
        );
        PatientVisitResponse response = new PatientVisitResponse(visitId, patientId, doctorId, visitDate, "Cough", "Cold", "Rest", "Notes");
        when(visitService.create(any(PatientVisitCreateOrUpdateRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/visits")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(visitId.toString()));

        verify(visitService).create(any(PatientVisitCreateOrUpdateRequest.class));
    }

    @Test
    @WithMockUser
    void update_ShouldUpdatePatientVisit() throws Exception {
        // Given
        PatientVisitCreateOrUpdateRequest request = new PatientVisitCreateOrUpdateRequest(
                patientId, doctorId, visitDate, "Fever", "Flu", "Medicine", "Updated notes"
        );
        PatientVisitResponse response = new PatientVisitResponse(visitId, patientId, doctorId, visitDate, "Fever", "Flu", "Medicine", "Updated notes");
        when(visitService.update(eq(visitId), any(PatientVisitCreateOrUpdateRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(put("/api/visits/{id}", visitId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.diagnosis").value("Flu"));

        verify(visitService).update(eq(visitId), any(PatientVisitCreateOrUpdateRequest.class));
    }

    @Test
    @WithMockUser
    void delete_ShouldDeletePatientVisit() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/visits/{id}", visitId)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(visitService).delete(visitId);
    }
}