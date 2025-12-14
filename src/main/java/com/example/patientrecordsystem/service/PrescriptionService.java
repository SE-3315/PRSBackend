package com.example.patientrecordsystem.service;

import com.example.patientrecordsystem.dto.PrescriptionCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.PrescriptionResponse;
import com.example.patientrecordsystem.entity.Doctor;
import com.example.patientrecordsystem.entity.Patient;
import com.example.patientrecordsystem.entity.Prescription;
import com.example.patientrecordsystem.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public PrescriptionService(PrescriptionRepository prescriptionRepository, PatientService patientService, DoctorService doctorService) {
        this.prescriptionRepository = prescriptionRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @Transactional
    public PrescriptionResponse create(PrescriptionCreateOrUpdateRequest req) {
        Prescription p = new Prescription();
        apply(p, req);
        return toResponse(prescriptionRepository.save(p));
    }

    @Transactional
    public PrescriptionResponse update(UUID id, PrescriptionCreateOrUpdateRequest req) {
        Prescription p = prescriptionRepository.findById(id).orElseThrow(() -> new NotFoundException("Prescription not found"));
        apply(p, req);
        return toResponse(prescriptionRepository.save(p));
    }

    @Transactional(readOnly = true)
    public PrescriptionResponse get(UUID id) {
        return prescriptionRepository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Prescription not found"));
    }

    @Transactional(readOnly = true)
    public List<PrescriptionResponse> list() {
        return prescriptionRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public void delete(UUID id) {
        if (!prescriptionRepository.existsById(id)) throw new NotFoundException("Prescription not found");
        prescriptionRepository.deleteById(id);
    }

    private void apply(Prescription p, PrescriptionCreateOrUpdateRequest req) {
        Patient patient = patientService.require(req.patientId());
        Doctor doctor = doctorService.require(req.doctorId());

        p.setPatient(patient);
        p.setDoctor(doctor);
        p.setMedicationName(req.medicationName());
        p.setDosage(req.dosage());
        p.setFrequency(req.frequency());
        p.setDuration(req.duration());
        p.setInstructions(req.instructions());
        p.setIssuedAt(req.issuedAt() != null ? req.issuedAt() : Instant.now());


    }

    private PrescriptionResponse toResponse(Prescription p) {
        return new PrescriptionResponse(
                p.getId(),
                p.getPatient().getId(),
                p.getDoctor().getId(),
                p.getMedicationName(),
                p.getDosage(),
                p.getFrequency(),
                p.getDuration(),
                p.getInstructions(),
                p.getIssuedAt()
        );
    }
}