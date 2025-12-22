package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.PatientCreateRequest;
import com.example.patientrecordsystem.dto.PatientResponse;
import com.example.patientrecordsystem.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller that manages patient records.
 *
 * <p>Exposes endpoints to list, retrieve, create, update and delete patients.
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Lists all patients.
     *
     * @return a list of patient responses
     */
    @GetMapping
    public List<PatientResponse> list() {
        return patientService.list();
    }

    /**
     * Retrieves a patient by id.
     *
     * @param id the patient id
     * @return the patient response
     */
    @GetMapping("/{id}")
    public PatientResponse get(@PathVariable UUID id) {
        return patientService.get(id);
    }

    /**
     * Creates a new patient.
     *
     * @param req the patient creation request
     * @return the created patient response
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponse create(@Valid @RequestBody PatientCreateRequest req) {
        return patientService.create(req);
    }

    /**
     * Updates an existing patient.
     *
     * @param id the patient id
     * @param req the patient update request
     * @return the updated patient response
     */
    @PutMapping("/{id}")
    public PatientResponse update(@PathVariable UUID id, @Valid @RequestBody PatientCreateRequest req) {
        return patientService.update(id, req);
    }

    /**
     * Deletes a patient.
     *
     * @param id the patient id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        patientService.delete(id);
    }
}