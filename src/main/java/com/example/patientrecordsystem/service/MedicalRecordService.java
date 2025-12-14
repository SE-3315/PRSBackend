package com.example.patientrecordsystem.service;

import com.example.patientrecordsystem.dto.MedicalRecordCreateOrUpdateRequest;
import com.example.patientrecordsystem.dto.MedicalRecordResponse;
import com.example.patientrecordsystem.entity.Doctor;
import com.example.patientrecordsystem.entity.MedicalRecord;
import com.example.patientrecordsystem.entity.Patient;
import com.example.patientrecordsystem.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository recordRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public MedicalRecordService(MedicalRecordRepository recordRepository, PatientService patientService, DoctorService doctorService) {
        this.recordRepository = recordRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @Transactional
    public MedicalRecordResponse create(MedicalRecordCreateOrUpdateRequest req) {
        MedicalRecord r = new MedicalRecord();
        apply(r, req);
        return toResponse(recordRepository.save(r));
    }

    @Transactional
    public MedicalRecordResponse update(UUID id, MedicalRecordCreateOrUpdateRequest req) {
        MedicalRecord r = recordRepository.findById(id).orElseThrow(() -> new NotFoundException("Medical record not found"));
        apply(r, req);
        return toResponse(recordRepository.save(r));
    }

    @Transactional(readOnly = true)
    public MedicalRecordResponse get(UUID id) {
        return recordRepository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Medical record not found"));
    }

    @Transactional(readOnly = true)
    public List<MedicalRecordResponse> list() {
        return recordRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public void delete(UUID id) {
        if (!recordRepository.existsById(id)) throw new NotFoundException("Medical record not found");
        recordRepository.deleteById(id);
    }

    private void apply(MedicalRecord r, MedicalRecordCreateOrUpdateRequest req) {
        Patient patient = patientService.require(req.patientId());
        Doctor doctor = doctorService.require(req.doctorId());

        r.setPatient(patient);
        r.setDoctor(doctor);
        r.setRecordType(req.recordType());
        r.setDescription(req.description());
        r.setAttachments(req.attachments());
    }
    private MedicalRecordResponse toResponse(MedicalRecord r) {
        return new MedicalRecordResponse(
                r.getId(),
                r.getPatient().getId(),
                r.getDoctor().getId(),
                r.getRecordType(),
                r.getDescription(),
                r.getAttachments()
        );
    }
}