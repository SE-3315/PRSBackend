package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.AppointmentCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.AppointmentResponse;
import com.example.patientrecordsystem.service.AppointmentService;
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

@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID appointmentId = UUID.randomUUID();
    private final UUID patientId = UUID.randomUUID();
    private final UUID doctorId = UUID.randomUUID();
    private final UUID departmentId = UUID.randomUUID();
    private final Instant appointmentDate = Instant.now();

    @Test
    @WithMockUser
    void list_ShouldReturnListOfAppointments() throws Exception {
        // Given
        List<AppointmentResponse> appointments = List.of(
                new AppointmentResponse(appointmentId, patientId, doctorId, departmentId, appointmentDate, "Scheduled", "Checkup")
        );
        when(appointmentService.list()).thenReturn(appointments);

        // When & Then
        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(appointmentId.toString()));

        verify(appointmentService).list();
    }

    @Test
    @WithMockUser
    void get_ShouldReturnAppointment() throws Exception {
        // Given
        AppointmentResponse response = new AppointmentResponse(appointmentId, patientId, doctorId, departmentId, appointmentDate, "Scheduled", "Checkup");
        when(appointmentService.get(appointmentId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/appointments/{id}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(appointmentId.toString()))
                .andExpect(jsonPath("$.status").value("Scheduled"));

        verify(appointmentService).get(appointmentId);
    }

    @Test
    @WithMockUser
    void create_ShouldCreateAppointment() throws Exception {
        // Given
        AppointmentCreateOrUpdateRequest request = new AppointmentCreateOrUpdateRequest(
                patientId, doctorId, departmentId, appointmentDate, "Scheduled", "Checkup"
        );
        AppointmentResponse response = new AppointmentResponse(appointmentId, patientId, doctorId, departmentId, appointmentDate, "Scheduled", "Checkup");
        when(appointmentService.create(any(AppointmentCreateOrUpdateRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/appointments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(appointmentId.toString()));

        verify(appointmentService).create(any(AppointmentCreateOrUpdateRequest.class));
    }

    @Test
    @WithMockUser
    void update_ShouldUpdateAppointment() throws Exception {
        // Given
        AppointmentCreateOrUpdateRequest request = new AppointmentCreateOrUpdateRequest(
                patientId, doctorId, departmentId, appointmentDate, "Completed", "Checkup"
        );
        AppointmentResponse response = new AppointmentResponse(appointmentId, patientId, doctorId, departmentId, appointmentDate, "Completed", "Checkup");
        when(appointmentService.update(eq(appointmentId), any(AppointmentCreateOrUpdateRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(put("/api/appointments/{id}", appointmentId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("Completed"));

        verify(appointmentService).update(eq(appointmentId), any(AppointmentCreateOrUpdateRequest.class));
    }

    @Test
    @WithMockUser
    void delete_ShouldDeleteAppointment() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/appointments/{id}", appointmentId)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(appointmentService).delete(appointmentId);
    }
}