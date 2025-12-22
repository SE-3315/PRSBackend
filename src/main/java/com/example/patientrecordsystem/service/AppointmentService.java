package com.example.patientrecordsystem.service;

import com.example.patientrecordsystem.dto.AppointmentCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.AppointmentResponse;
import com.example.patientrecordsystem.entity.Appointment;
import com.example.patientrecordsystem.entity.Department;
import com.example.patientrecordsystem.entity.Doctor;
import com.example.patientrecordsystem.entity.Patient;
import com.example.patientrecordsystem.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Service handling business logic for appointments.
 *
 * <p>Provides create, update, retrieve, list and delete operations and converts
 * between entities and DTOs.
 */
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientService patientService, DoctorService doctorService, DepartmentService departmentService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.departmentService = departmentService;
    }

    /**
     * Creates a new appointment.
     *
     * @param req the appointment creation request containing patient, doctor, department and appointment details
     * @return the created appointment response
     * @throws NotFoundException if patient, doctor or department is not found
     */
    @Transactional
    public AppointmentResponse create(AppointmentCreateOrUpdateRequest req) {
        Appointment a = new Appointment();
        apply(a, req);
        return toResponse(appointmentRepository.save(a));
    }

    /**
     * Updates an existing appointment.
     *
     * @param id the appointment id
     * @param req the appointment update request
     * @return the updated appointment response
     * @throws NotFoundException if appointment, patient, doctor or department is not found
     */
    @Transactional
    public AppointmentResponse update(UUID id, AppointmentCreateOrUpdateRequest req) {
        Appointment a = appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));
        apply(a, req);
        return toResponse(appointmentRepository.save(a));
    }

    /**
     * Retrieves an appointment by id.
     *
     * @param id the appointment id
     * @return the appointment response
     * @throws NotFoundException if appointment is not found
     */
    @Transactional(readOnly = true)
    public AppointmentResponse get(UUID id) {
        return appointmentRepository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Appointment not found"));
    }

    /**
     * Lists all appointments.
     *
     * @return a list of all appointment responses
     */
    @Transactional(readOnly = true)
    public List<AppointmentResponse> list() {
        return appointmentRepository.findAll().stream().map(this::toResponse).toList();
    }

    /**
     * Deletes an appointment.
     *
     * @param id the appointment id
     * @throws NotFoundException if appointment is not found
     */
    @Transactional
    public void delete(UUID id) {
        if (!appointmentRepository.existsById(id)) throw new NotFoundException("Appointment not found");
        appointmentRepository.deleteById(id);
    }

    private void apply(Appointment a, AppointmentCreateOrUpdateRequest req) {
        Patient patient = patientService.require(req.patientId());
        Doctor doctor = doctorService.require(req.doctorId());
        Department dept = departmentService.require(req.departmentId());
        a.setPatient(patient);
        a.setDoctor(doctor);
        a.setDepartment(dept);
        a.setAppointmentDate(req.appointmentDate());
        a.setStatus(req.status());
        a.setReason(req.reason());
    }
    private AppointmentResponse toResponse(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getPatient().getId(),
                a.getDoctor().getId(),
                a.getDepartment().getId(),
                a.getAppointmentDate(),
                a.getStatus(),
                a.getReason()
        );
    }
}