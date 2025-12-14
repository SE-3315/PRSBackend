package com.example.patientrecordsystem.service;

import com.example.patientrecordsystem.dto.PatientVisitCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.PatientVisitResponse;
import com.example.patientrecordsystem.entity.Doctor;
import com.example.patientrecordsystem.entity.Patient;
import com.example.patientrecordsystem.entity.PatientVisit;
import com.example.patientrecordsystem.repository.PatientVisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class PatientVisitService {

    private final PatientVisitRepository visitRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public PatientVisitService(PatientVisitRepository visitRepository, PatientService patientService, DoctorService doctorService) {
        this.visitRepository = visitRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @Transactional
    public PatientVisitResponse create(PatientVisitCreateOrUpdateRequest req) {
        PatientVisit v = new PatientVisit();
        apply(v, req);
        return toResponse(visitRepository.save(v));
    }

    @Transactional
    public PatientVisitResponse update(UUID id, PatientVisitCreateOrUpdateRequest req) {
        PatientVisit v = visitRepository.findById(id).orElseThrow(() -> new NotFoundException("Visit not found"));
        apply(v, req);
        return toResponse(visitRepository.save(v));
    }

    @Transactional(readOnly = true)
    public PatientVisitResponse get(UUID id) {
        return visitRepository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Visit not found"));
    }

    @Transactional(readOnly = true)
    public List<PatientVisitResponse> list() {
        return visitRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public void delete(UUID id) {
        if (!visitRepository.existsById(id)) throw new NotFoundException("Visit not found");
        visitRepository.deleteById(id);
    }

    private void apply(PatientVisit v, PatientVisitCreateOrUpdateRequest req) {
        Patient patient = patientService.require(req.patientId());
        Doctor doctor = doctorService.require(req.doctorId());

        v.setPatient(patient);
        v.setDoctor(doctor);
        v.setVisitDate(req.visitDate());
        v.setSymptoms(req.symptoms());
        v.setDiagnosis(req.diagnosis());
        v.setTreatmentPlan(req.treatmentPlan());
        v.setNotes(req.notes());


    }

    private PatientVisitResponse toResponse(PatientVisit v) {
        return new PatientVisitResponse(
                v.getId(),
                v.getPatient().getId(),
                v.getDoctor().getId(),
                v.getVisitDate(),
                v.getSymptoms(),
                v.getDiagnosis(),
                v.getTreatmentPlan(),
                v.getNotes()
        );
    }
}