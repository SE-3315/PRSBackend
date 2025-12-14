package com.example.patientrecordsystem.service;

import com.example.patientrecordsystem.dto.DepartmentCreateRequest;
import com.example.patientrecordsystem.dto.DepartmentResponse;
import com.example.patientrecordsystem.entity.Department;
import com.example.patientrecordsystem.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public DepartmentResponse create(DepartmentCreateRequest req) {
        if (departmentRepository.existsByNameIgnoreCase(req.name())) {
            throw new IllegalArgumentException("Department name already exists");
        }
        Department d = new Department(req.name().trim(), req.description(), req.isActive());
        Department saved = departmentRepository.save(d);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> list() {
        return departmentRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Department require(UUID id) {
        return departmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Department not found"));
    }

    @Transactional
    public void delete(UUID id) {
        if (!departmentRepository.existsById(id)) throw new NotFoundException("Department not found");
        departmentRepository.deleteById(id);
    }

    private DepartmentResponse toResponse(Department d) {
        return new DepartmentResponse(d.getId(), d.getName(), d.getDescription(), d.getActive());
    }
}