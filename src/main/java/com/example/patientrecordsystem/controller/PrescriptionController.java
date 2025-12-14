package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.PrescriptionCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.PrescriptionResponse;
import com.example.patientrecordsystem.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping
    public List<PrescriptionResponse> list() {
        return prescriptionService.list();
    }

    @GetMapping("/{id}")
    public PrescriptionResponse get(@PathVariable UUID id) {
        return prescriptionService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrescriptionResponse create(@Valid @RequestBody PrescriptionCreateOrUpdateRequest req) {
        return prescriptionService.create(req);
    }

    @PutMapping("/{id}")
    public PrescriptionResponse update(@PathVariable UUID id, @Valid @RequestBody PrescriptionCreateOrUpdateRequest req) {
        return prescriptionService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        prescriptionService.delete(id);
    }

}
