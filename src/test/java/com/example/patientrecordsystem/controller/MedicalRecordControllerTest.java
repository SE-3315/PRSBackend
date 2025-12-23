package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.MedicalRecordCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.MedicalRecordResponse;
import com.example.patientrecordsystem.service.MedicalRecordService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicalRecordController.class)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService recordService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID recordId = UUID.randomUUID();
    private final UUID patientId = UUID.randomUUID();
    private final UUID doctorId = UUID.randomUUID();

    @Test
    @WithMockUser
    void list_ShouldReturnListOfMedicalRecords() throws Exception {
        // Given
        List<MedicalRecordResponse> records = List.of(
                new MedicalRecordResponse(recordId, patientId, doctorId, "Diagnosis", "Patient has flu", "attachment.pdf")
        );
        when(recordService.list()).thenReturn(records);

        // When & Then
        mockMvc.perform(get("/api/records"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].recordType").value("Diagnosis"));

        verify(recordService).list();
    }

    @Test
    @WithMockUser
    void get_ShouldReturnMedicalRecord() throws Exception {
        // Given
        MedicalRecordResponse response = new MedicalRecordResponse(recordId, patientId, doctorId, "Diagnosis", "Patient has flu", "attachment.pdf");
        when(recordService.get(recordId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/records/{id}", recordId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(recordId.toString()))
                .andExpect(jsonPath("$.description").value("Patient has flu"));

        verify(recordService).get(recordId);
    }

    @Test
    @WithMockUser
    void create_ShouldCreateMedicalRecord() throws Exception {
        // Given
        MedicalRecordCreateOrUpdateRequest request = new MedicalRecordCreateOrUpdateRequest(
                patientId, doctorId, "Diagnosis", "Patient has flu", "attachment.pdf"
        );
        MedicalRecordResponse response = new MedicalRecordResponse(recordId, patientId, doctorId, "Diagnosis", "Patient has flu", "attachment.pdf");
        when(recordService.create(any(MedicalRecordCreateOrUpdateRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/records")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(recordId.toString()));

        verify(recordService).create(any(MedicalRecordCreateOrUpdateRequest.class));
    }

    @Test
    @WithMockUser
    void update_ShouldUpdateMedicalRecord() throws Exception {
        // Given
        MedicalRecordCreateOrUpdateRequest request = new MedicalRecordCreateOrUpdateRequest(
                patientId, doctorId, "Treatment", "Prescribed medicine", "attachment.pdf"
        );
        MedicalRecordResponse response = new MedicalRecordResponse(recordId, patientId, doctorId, "Treatment", "Prescribed medicine", "attachment.pdf");
        when(recordService.update(eq(recordId), any(MedicalRecordCreateOrUpdateRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(put("/api/records/{id}", recordId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.recordType").value("Treatment"));

        verify(recordService).update(eq(recordId), any(MedicalRecordCreateOrUpdateRequest.class));
    }

    @Test
    @WithMockUser
    void delete_ShouldDeleteMedicalRecord() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/records/{id}", recordId)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(recordService).delete(recordId);
    }
}