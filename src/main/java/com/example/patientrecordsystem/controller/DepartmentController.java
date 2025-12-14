package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.DepartmentCreateRequest;
import com.example.patientrecordsystem.dto.DepartmentResponse;
import com.example.patientrecordsystem.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse create(@Valid @RequestBody DepartmentCreateRequest req) {
        return departmentService.create(req);
    }

    @GetMapping
    public List<DepartmentResponse> list() {
        return departmentService.list();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        departmentService.delete(id);
    }
}