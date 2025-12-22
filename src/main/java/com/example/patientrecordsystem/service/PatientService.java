package com.example.patientrecordsystem.service;

import com.example.patientrecordsystem.dto.PatientCreateRequest;
import com.example.patientrecordsystem.dto.PatientResponse;
import com.example.patientrecordsystem.entity.Department;
import com.example.patientrecordsystem.entity.Doctor;
import com.example.patientrecordsystem.entity.Patient;
import com.example.patientrecordsystem.repository.DepartmentRepository;
import com.example.patientrecordsystem.repository.DoctorRepository;
import com.example.patientrecordsystem.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service managing patient entities and related business logic.
 *
 * <p>Supports create, update, retrieve, list, delete and helper methods to require entities.
 */
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    public PatientService(PatientRepository patientRepository, DoctorService doctorService, DepartmentService departmentService) {
        this.patientRepository = patientRepository;
        this.doctorService = doctorService;
        this.departmentService = departmentService;
    }

    /**
     * Creates a new patient.
     *
     * @param req the patient creation request with personal and medical details
     * @return the created patient response
     * @throws IllegalArgumentException if national ID is not unique
     * @throws NotFoundException if primary doctor or department is not found
     */
    @Transactional
    public PatientResponse create(PatientCreateRequest req) {
        if (req.nationalId() != null && patientRepository.existsByNationalId(req.nationalId())) {
            throw new IllegalArgumentException("National ID must be unique");
        }
        Patient p = new Patient();
        apply(p, req, true);
        return toResponse(patientRepository.save(p));
    }

    /**
     * Updates an existing patient.
     *
     * @param id the patient id
     * @param req the patient update request
     * @return the updated patient response
     * @throws IllegalArgumentException if national ID is not unique
     * @throws NotFoundException if patient, primary doctor or department is not found
     */
    @Transactional
    public PatientResponse update(UUID id, PatientCreateRequest req) {
        Patient p = patientRepository.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
        if (req.nationalId() != null && patientRepository.existsByNationalId(req.nationalId())) {
            throw new IllegalArgumentException("National ID must be unique");
        }
        apply(p, req, false);
        return toResponse(patientRepository.save(p));
    }

    /**
     * Retrieves a patient by id.
     *
     * @param id the patient id
     * @return the patient response
     * @throws NotFoundException if patient is not found
     */
    @Transactional(readOnly = true)
    public PatientResponse get(UUID id) {
        return patientRepository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Patient not found"));
    }

    /**
     * Lists all patients.
     *
     * @return a list of all patient responses
     */
    @Transactional(readOnly = true)
    public List<PatientResponse> list() {
        return patientRepository.findAll().stream().map(this::toResponse).toList();
    }

    /**
     * Deletes a patient.
     *
     * @param id the patient id
     * @throws NotFoundException if patient is not found
     */
    @Transactional
    public void delete(UUID id) {
        if (!patientRepository.existsById(id)) throw new NotFoundException("Patient not found");
        patientRepository.deleteById(id);
    }

    /**
     * Retrieves a patient by id (internal use).
     *
     * @param id the patient id
     * @return the patient entity
     * @throws NotFoundException if patient is not found
     */
    @Transactional(readOnly = true)
    public Patient require(UUID id) {
        return patientRepository.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
    }

    private void apply(Patient p, PatientCreateRequest req, boolean isCreate) {
        p.setFirstName(req.firstName());
        p.setLastName(req.lastName());
        p.setNationalId(req.nationalId());
        p.setBirthDate(req.birthDate());
        p.setGender(req.gender());
        p.setPhone(req.phone());
        p.setEmail(req.email());
        p.setAddress(req.address());
        p.setBloodType(req.bloodType());
        p.setAllergies(req.allergies());
        p.setChronicConditions(req.chronicConditions());
        p.setInsuranceProvider(req.insuranceProvider());
        p.setInsuranceNumber(req.insuranceNumber());
        p.setEmergencyContactName(req.emergencyContactName());
        p.setEmergencyContactPhone(req.emergencyContactPhone());

        Doctor doctor = null;
        if (req.primaryDoctorId() != null) doctor = doctorService.require(req.primaryDoctorId());
        p.setPrimaryDoctor(doctor);

        Department dept = null;
        if (req.departmentId() != null) dept = departmentService.require(req.departmentId());
        p.setDepartment(dept);


    }

    private PatientResponse toResponse(Patient p) {
        UUID doctorId = p.getPrimaryDoctor() != null ? p.getPrimaryDoctor().getId() : null;
        UUID deptId = p.getDepartment() != null ? p.getDepartment().getId() : null;
        return new PatientResponse(
                p.getId(),
                p.getFirstName(),
                p.getLastName(),
                p.getNationalId(),
              //  p.getBirthDate(),
                p.getGender(),
                p.getPhone(),
             //   p.getEmail(),
             //   p.getAddress(),
            //    p.getBloodType(),
            //    p.getAllergies(),
            //    p.getChronicConditions(),
            //    p.getInsuranceProvider(),
            //    p.getInsuranceNumber(),
            //    p.getEmergencyContactName(),
            //    p.getEmergencyContactPhone(),
                doctorId,
                deptId
        );


    }
}