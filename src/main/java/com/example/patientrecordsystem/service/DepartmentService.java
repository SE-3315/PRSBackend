package com.example.patientrecordsystem.service;

import com.example.patientrecordsystem.dto.DepartmentCreateRequest;
import com.example.patientrecordsystem.dto.DepartmentResponse;
import com.example.patientrecordsystem.entity.Department;
import com.example.patientrecordsystem.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Service that manages departments.
 *
 * <p>Supports creating, listing, and deleting departments and converting to response DTOs.
 */
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Creates a new department.
     *
     * @param req the department creation request
     * @return the created department response
     * @throws IllegalArgumentException if department name already exists
     */
    @Transactional
    public DepartmentResponse create(DepartmentCreateRequest req) {
        if (departmentRepository.existsByNameIgnoreCase(req.name())) {
            throw new IllegalArgumentException("Department name already exists");
        }
        Department d = new Department(req.name(), req.description(), req.isActive());
        Department saved = departmentRepository.save(d);
        return toResponse(saved);
    }

    /**
     * Lists all departments.
     *
     * @return a list of all department responses
     */
    @Transactional(readOnly = true)
    public List<DepartmentResponse> list() {
        return departmentRepository.findAll().stream().map(this::toResponse).toList();
    }

    /**
     * Retrieves a department by id (internal use).
     *
     * @param id the department id
     * @return the department entity
     * @throws NotFoundException if department is not found
     */
    @Transactional(readOnly = true)
    public Department require(UUID id) {
        return departmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Department not found"));
    }

    /**
     * Deletes a department.
     *
     * @param id the department id
     * @throws NotFoundException if department is not found
     */
    @Transactional
    public void delete(UUID id) {
        if (!departmentRepository.existsById(id)) throw new NotFoundException("Department not found");
        departmentRepository.deleteById(id);
    }

    private DepartmentResponse toResponse(Department d) {
        return new DepartmentResponse(d.getId(), d.getName(), d.getDescription(), d.getActive());
    }
}