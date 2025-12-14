package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.PatientVisitCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.PatientVisitResponse;
import com.example.patientrecordsystem.service.PatientVisitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/visits")
public class PatientVisitController {

    private final PatientVisitService visitService;

    public PatientVisitController(PatientVisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping
    public List<PatientVisitResponse> list() {
        return visitService.list();
    }

    @GetMapping("/{id}")
    public PatientVisitResponse get(@PathVariable UUID id) {
        return visitService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientVisitResponse create(@Valid @RequestBody PatientVisitCreateOrUpdateRequest req) {
        return visitService.create(req);
    }

    @PutMapping("/{id}")
    public PatientVisitResponse update(@PathVariable UUID id, @Valid @RequestBody PatientVisitCreateOrUpdateRequest req) {
        return visitService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        visitService.delete(id);
    }
}