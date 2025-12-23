package com.example.patientrecordsystem.controller;

import com.example.patientrecordsystem.dto.DoctorCreateRequest;
import com.example.patientrecordsystem.dto.DoctorResponse;
import com.example.patientrecordsystem.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing doctors.
 *
 * <p>Exposes endpoints to create, list and delete doctor records.
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Creates a new doctor record.
     *
     * @param req the doctor creation request
     * @return the created doctor response
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponse create(@Valid @RequestBody DoctorCreateRequest req) {
        return doctorService.create(req);
    }

    /**
     * Lists all doctors.
     *
     * @return a list of all doctor responses
     */
    @GetMapping
    public List<DoctorResponse> list() {
        return doctorService.list();
    }

    /**
     * Deletes a doctor.
     *
     * @param id the doctor id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        doctorService.delete(id);
    }
}