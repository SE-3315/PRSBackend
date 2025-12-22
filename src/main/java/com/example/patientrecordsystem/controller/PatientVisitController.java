package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.PatientVisitCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.PatientVisitResponse;
import com.example.patientrecordsystem.service.PatientVisitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for patient visit records.
 *
 * <p>Provides endpoints for listing, retrieving, creating, updating and deleting patient visits.
 */
@RestController
@RequestMapping("/api/visits")
public class PatientVisitController {

    private final PatientVisitService visitService;

    public PatientVisitController(PatientVisitService visitService) {
        this.visitService = visitService;
    }

    /**
     * Lists all patient visits.
     *
     * @return a list of patient visit responses
     */
    @GetMapping
    public List<PatientVisitResponse> list() {
        return visitService.list();
    }

    /**
     * Retrieves a patient visit by id.
     *
     * @param id the patient visit id
     * @return the patient visit response
     */
    @GetMapping("/{id}")
    public PatientVisitResponse get(@PathVariable UUID id) {
        return visitService.get(id);
    }

    /**
     * Creates a new patient visit.
     *
     * @param req the patient visit creation request
     * @return the created patient visit response
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientVisitResponse create(@Valid @RequestBody PatientVisitCreateOrUpdateRequest req) {
        return visitService.create(req);
    }

    /**
     * Updates an existing patient visit.
     *
     * @param id the patient visit id
     * @param req the patient visit update request
     * @return the updated patient visit response
     */
    @PutMapping("/{id}")
    public PatientVisitResponse update(@PathVariable UUID id, @Valid @RequestBody PatientVisitCreateOrUpdateRequest req) {
        return visitService.update(id, req);
    }

    /**
     * Deletes a patient visit.
     *
     * @param id the patient visit id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        visitService.delete(id);
    }
}