package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.MedicalRecordCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.MedicalRecordResponse;
import com.example.patientrecordsystem.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/records")
public class MedicalRecordController {

    private final MedicalRecordService recordService;

    public MedicalRecordController(MedicalRecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping
    public List<MedicalRecordResponse> list() {
        return recordService.list();
    }

    @GetMapping("/{id}")
    public MedicalRecordResponse get(@PathVariable UUID id) {
        return recordService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalRecordResponse create(@Valid @RequestBody MedicalRecordCreateOrUpdateRequest req) {
        return recordService.create(req);
    }

    @PutMapping("/{id}")
    public MedicalRecordResponse update(@PathVariable UUID id, @Valid @RequestBody MedicalRecordCreateOrUpdateRequest req) {
        return recordService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        recordService.delete(id);
    }
}