package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.PrescriptionCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.PrescriptionResponse;
import com.example.patientrecordsystem.service.PrescriptionService;
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

@WebMvcTest(PrescriptionController.class)
class PrescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrescriptionService prescriptionService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID prescriptionId = UUID.randomUUID();
    private final UUID patientId = UUID.randomUUID();
    private final UUID doctorId = UUID.randomUUID();
    private final Instant issuedAt = Instant.now();

    @Test
    @WithMockUser
    void list_ShouldReturnListOfPrescriptions() throws Exception {
        // Given
        List<PrescriptionResponse> prescriptions = List.of(
                new PrescriptionResponse(prescriptionId, patientId, doctorId, "Aspirin", "100mg", "Twice daily", "7 days", "Take with food", issuedAt)
        );
        when(prescriptionService.list()).thenReturn(prescriptions);

        // When & Then
        mockMvc.perform(get("/api/prescriptions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].medicationName").value("Aspirin"));

        verify(prescriptionService).list();
    }

    @Test
    @WithMockUser
    void get_ShouldReturnPrescription() throws Exception {
        // Given
        PrescriptionResponse response = new PrescriptionResponse(prescriptionId, patientId, doctorId, "Aspirin", "100mg", "Twice daily", "7 days", "Take with food", issuedAt);
        when(prescriptionService.get(prescriptionId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/prescriptions/{id}", prescriptionId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(prescriptionId.toString()))
                .andExpect(jsonPath("$.dosage").value("100mg"));

        verify(prescriptionService).get(prescriptionId);
    }

    @Test
    @WithMockUser
    void create_ShouldCreatePrescription() throws Exception {
        // Given
        PrescriptionCreateOrUpdateRequest request = new PrescriptionCreateOrUpdateRequest(
                patientId, doctorId, "Aspirin", "100mg", "Twice daily", "7 days", "Take with food", issuedAt
        );
        PrescriptionResponse response = new PrescriptionResponse(prescriptionId, patientId, doctorId, "Aspirin", "100mg", "Twice daily", "7 days", "Take with food", issuedAt);
        when(prescriptionService.create(any(PrescriptionCreateOrUpdateRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/prescriptions")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(prescriptionId.toString()));

        verify(prescriptionService).create(any(PrescriptionCreateOrUpdateRequest.class));
    }

    @Test
    @WithMockUser
    void update_ShouldUpdatePrescription() throws Exception {
        // Given
        PrescriptionCreateOrUpdateRequest request = new PrescriptionCreateOrUpdateRequest(
                patientId, doctorId, "Ibuprofen", "200mg", "Three times daily", "10 days", "Take after meals", issuedAt
        );
        PrescriptionResponse response = new PrescriptionResponse(prescriptionId, patientId, doctorId, "Ibuprofen", "200mg", "Three times daily", "10 days", "Take after meals", issuedAt);
        when(prescriptionService.update(eq(prescriptionId), any(PrescriptionCreateOrUpdateRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(put("/api/prescriptions/{id}", prescriptionId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.medicationName").value("Ibuprofen"));

        verify(prescriptionService).update(eq(prescriptionId), any(PrescriptionCreateOrUpdateRequest.class));
    }

    @Test
    @WithMockUser
    void delete_ShouldDeletePrescription() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/prescriptions/{id}", prescriptionId)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(prescriptionService).delete(prescriptionId);
    }
}