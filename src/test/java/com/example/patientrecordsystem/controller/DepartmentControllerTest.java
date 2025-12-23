package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.DepartmentCreateRequest;
import com.example.patientrecordsystem.dto.DepartmentResponse;
import com.example.patientrecordsystem.service.DepartmentService;
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

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID departmentId = UUID.randomUUID();

    @Test
    @WithMockUser
    void create_ShouldCreateDepartment() throws Exception {
        // Given
        DepartmentCreateRequest request = new DepartmentCreateRequest("Cardiology", "Heart department", true);
        DepartmentResponse response = new DepartmentResponse(departmentId, "Cardiology", "Heart department", true);
        when(departmentService.create(any(DepartmentCreateRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/departments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(departmentId.toString()))
                .andExpect(jsonPath("$.name").value("Cardiology"));

        verify(departmentService).create(any(DepartmentCreateRequest.class));
    }

    @Test
    @WithMockUser
    void list_ShouldReturnListOfDepartments() throws Exception {
        // Given
        List<DepartmentResponse> departments = List.of(
                new DepartmentResponse(departmentId, "Cardiology", "Heart department", true)
        );
        when(departmentService.list()).thenReturn(departments);

        // When & Then
        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Cardiology"));

        verify(departmentService).list();
    }

    @Test
    @WithMockUser
    void delete_ShouldDeleteDepartment() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/departments/{id}", departmentId)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(departmentService).delete(departmentId);
    }
}