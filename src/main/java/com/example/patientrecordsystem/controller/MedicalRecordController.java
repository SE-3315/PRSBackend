package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.MedicalRecordCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.MedicalRecordResponse;
import com.example.patientrecordsystem.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for medical record management.
 *
 * <p>Provides CRUD endpoints for medical records associated with patients.
 */
@RestController
@RequestMapping("/api/records")
public class MedicalRecordController {

    private final MedicalRecordService recordService;

    public MedicalRecordController(MedicalRecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * Lists all medical records.
     *
     * @return a list of medical record responses
     */
    @GetMapping
    public List<MedicalRecordResponse> list() {
        return recordService.list();
    }

    /**
     * Retrieves a medical record by id.
     *
     * @param id the medical record id
     * @return the medical record response
     */
    @GetMapping("/{id}")
    public MedicalRecordResponse get(@PathVariable UUID id) {
        return recordService.get(id);
    }

    /**
     * Creates a new medical record.
     *
     * @param req the medical record creation request
     * @return the created medical record response
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalRecordResponse create(@Valid @RequestBody MedicalRecordCreateOrUpdateRequest req) {
        return recordService.create(req);
    }

    /**
     * Updates an existing medical record.
     *
     * @param id the medical record id
     * @param req the medical record update request
     * @return the updated medical record response
     */
    @PutMapping("/{id}")
    public MedicalRecordResponse update(@PathVariable UUID id, @Valid @RequestBody MedicalRecordCreateOrUpdateRequest req) {
        return recordService.update(id, req);
    }

    /**
     * Deletes a medical record.
     *
     * @param id the medical record id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        recordService.delete(id);
    }
}