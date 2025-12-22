package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.AppointmentCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.AppointmentResponse;
import com.example.patientrecordsystem.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing appointments.
 *
 * <p>Exposes endpoints to list all appointments, retrieve a single appointment by id,
 * create new appointments, update existing ones and delete appointments.
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Lists all appointments.
     *
     * @return a list of appointment responses
     */
    @GetMapping
    public List<AppointmentResponse> list() {
        return appointmentService.list();
    }

    /**
     * Retrieves an appointment by id.
     *
     * @param id the appointment id
     * @return the appointment response
     */
    @GetMapping("/{id}")
    public AppointmentResponse get(@PathVariable UUID id) {
        return appointmentService.get(id);
    }

    /**
     * Creates a new appointment.
     *
     * @param req the appointment creation request
     * @return the created appointment response
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponse create(@Valid @RequestBody AppointmentCreateOrUpdateRequest req) {
        return appointmentService.create(req);
    }

    /**
     * Updates an existing appointment.
     *
     * @param id the appointment id
     * @param req the appointment update request
     * @return the updated appointment response
     */
    @PutMapping("/{id}")
    public AppointmentResponse update(@PathVariable UUID id, @Valid @RequestBody AppointmentCreateOrUpdateRequest req) {
        return appointmentService.update(id, req);
    }

    /**
     * Deletes an appointment.
     *
     * @param id the appointment id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        appointmentService.delete(id);
    }
}