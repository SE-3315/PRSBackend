package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.DepartmentCreateRequest;
import com.example.patientrecordsystem.dto.DepartmentResponse;
import com.example.patientrecordsystem.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for department management.
 *
 * <p>Supports creating new departments, listing departments and deleting departments.
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Creates a new department.
     *
     * @param req the department creation request
     * @return the created department response
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse create(@Valid @RequestBody DepartmentCreateRequest req) {
        return departmentService.create(req);
    }

    /**
     * Lists all departments.
     *
     * @return a list of all department responses
     */
    @GetMapping
    public List<DepartmentResponse> list() {
        return departmentService.list();
    }

    /**
     * Deletes a department.
     *
     * @param id the department id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        departmentService.delete(id);
    }
}