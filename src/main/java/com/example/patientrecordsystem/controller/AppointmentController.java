package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.AppointmentCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.AppointmentResponse;
import com.example.patientrecordsystem.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<AppointmentResponse> list() {
        return appointmentService.list();
    }

    @GetMapping("/{id}")
    public AppointmentResponse get(@PathVariable UUID id) {
        return appointmentService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponse create(@Valid @RequestBody AppointmentCreateOrUpdateRequest req) {
        return appointmentService.create(req);
    }

    @PutMapping("/{id}")
    public AppointmentResponse update(@PathVariable UUID id, @Valid @RequestBody AppointmentCreateOrUpdateRequest req) {
        return appointmentService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        appointmentService.delete(id);
    }
}