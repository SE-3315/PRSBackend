package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.PrescriptionCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.PrescriptionResponse;
import com.example.patientrecordsystem.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for prescriptions.
 *
 * <p>Exposes endpoints to list, retrieve, create, update and delete prescriptions for patients.
 */
@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    /**
     * Lists all prescriptions.
     *
     * @return a list of prescription responses
     */
    @GetMapping
    public List<PrescriptionResponse> list() {
        return prescriptionService.list();
    }

    /**
     * Retrieves a prescription by id.
     *
     * @param id the prescription id
     * @return the prescription response
     */
    @GetMapping("/{id}")
    public PrescriptionResponse get(@PathVariable UUID id) {
        return prescriptionService.get(id);
    }

    /**
     * Creates a new prescription.
     *
     * @param req the prescription creation request
     * @return the created prescription response
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrescriptionResponse create(@Valid @RequestBody PrescriptionCreateOrUpdateRequest req) {
        return prescriptionService.create(req);
    }

    /**
     * Updates an existing prescription.
     *
     * @param id the prescription id
     * @param req the prescription update request
     * @return the updated prescription response
     */
    @PutMapping("/{id}")
    public PrescriptionResponse update(@PathVariable UUID id, @Valid @RequestBody PrescriptionCreateOrUpdateRequest req) {
        return prescriptionService.update(id, req);
    }

    /**
     * Deletes a prescription.
     *
     * @param id the prescription id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        prescriptionService.delete(id);
    }

}
