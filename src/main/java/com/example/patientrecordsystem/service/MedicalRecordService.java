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

/**
 * Service for managing medical records.
 *
 * <p>Provides CRUD operations and maps between entities and DTOs.
 */
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

    /**
     * Creates a new medical record.
     *
     * @param req the medical record creation request
     * @return the created medical record response
     * @throws NotFoundException if patient or doctor is not found
     */
    @Transactional
    public MedicalRecordResponse create(MedicalRecordCreateOrUpdateRequest req) {
        MedicalRecord r = new MedicalRecord();
        apply(r, req);
        return toResponse(recordRepository.save(r));
    }

    /**
     * Updates an existing medical record.
     *
     * @param id the medical record id
     * @param req the medical record update request
     * @return the updated medical record response
     * @throws NotFoundException if record, patient or doctor is not found
     */
    @Transactional
    public MedicalRecordResponse update(UUID id, MedicalRecordCreateOrUpdateRequest req) {
        MedicalRecord r = recordRepository.findById(id).orElseThrow(() -> new NotFoundException("Medical record not found"));
        apply(r, req);
        return toResponse(recordRepository.save(r));
    }

    /**
     * Retrieves a medical record by id.
     *
     * @param id the medical record id
     * @return the medical record response
     * @throws NotFoundException if medical record is not found
     */
    @Transactional(readOnly = true)
    public MedicalRecordResponse get(UUID id) {
        return recordRepository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Medical record not found"));
    }

    /**
     * Lists all medical records.
     *
     * @return a list of all medical record responses
     */
    @Transactional(readOnly = true)
    public List<MedicalRecordResponse> list() {
        return recordRepository.findAll().stream().map(this::toResponse).toList();
    }

    /**
     * Deletes a medical record.
     *
     * @param id the medical record id
     * @throws NotFoundException if medical record is not found
     */
    @Transactional
    public void delete(UUID id) {
        if (!recordRepository.existsById(id)) throw new NotFoundException("Medical record not found");
        recordRepository.deleteById(id);
    }

    @Transactional
    public void deleteByPatientId(UUID patientID) {
        recordRepository.deleteByPatient_Id(patientID);
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