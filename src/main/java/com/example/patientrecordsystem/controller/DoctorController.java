package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.DoctorCreateRequest;
import com.example.patientrecordsystem.dto.DoctorResponse;
import com.example.patientrecordsystem.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponse create(@Valid @RequestBody DoctorCreateRequest req) {
        return doctorService.create(req);
    }

    @GetMapping
    public List<DoctorResponse> list() {
        return doctorService.list();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        doctorService.delete(id);
    }
}