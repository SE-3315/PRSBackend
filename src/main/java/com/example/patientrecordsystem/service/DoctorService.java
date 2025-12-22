package com.example.patientrecordsystem.service;

import com.example.patientrecordsystem.dto.DoctorCreateRequest;
import com.example.patientrecordsystem.dto.DoctorResponse;
import com.example.patientrecordsystem.entity.Department;
import com.example.patientrecordsystem.entity.Doctor;
import com.example.patientrecordsystem.entity.User;
import com.example.patientrecordsystem.repository.DoctorRepository;
import com.example.patientrecordsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Service that manages doctor records.
 *
 * <p>Provides create, list, require and delete operations and maps entities to DTOs.
 */
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DepartmentService departmentService;

    public DoctorService(DoctorRepository doctorRepository, UserRepository userRepository, DepartmentService departmentService) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.departmentService = departmentService;
    }

    /**
     * Creates a new doctor record.
     *
     * @param req the doctor creation request with user, department and license information
     * @return the created doctor response
     * @throws IllegalArgumentException if license number already exists
     * @throws NotFoundException if user or department is not found
     */
    @Transactional
    public DoctorResponse create(DoctorCreateRequest req) {
        if (doctorRepository.existsByLicenseNumber(req.licenseNumber())) {
            throw new IllegalArgumentException("License number already exists");
        }
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Department dept = departmentService.require(req.departmentId());

        Doctor d = new Doctor();
        d.setUser(user);
        d.setDepartment(dept);
        d.setLicenseNumber(req.licenseNumber().trim());
        d.setSpecialization(req.specialization());
        d.setPhone(req.phone());
        d.setEmail(req.email());
        d.setRoomNumber(req.roomNumber());
        d.setWorkingHours(req.workingHours());
        d.setBiography(req.biography());
        if (req.isActive() != null) d.setActive(req.isActive());

        return toResponse(doctorRepository.save(d));
    }

    /**
     * Lists all doctors.
     *
     * @return a list of all doctor responses
     */
    @Transactional(readOnly = true)
    public List<DoctorResponse> list() {
        return doctorRepository.findAll().stream().map(this::toResponse).toList();
    }

    /**
     * Retrieves a doctor by id (internal use).
     *
     * @param id the doctor id
     * @return the doctor entity
     * @throws NotFoundException if doctor is not found
     */
    @Transactional(readOnly = true)
    public Doctor require(UUID id) {
        return doctorRepository.findById(id).orElseThrow(() -> new NotFoundException("Doctor not found"));
    }

    /**
     * Deletes a doctor.
     *
     * @param id the doctor id
     * @throws NotFoundException if doctor is not found
     */
    @Transactional
    public void delete(UUID id) {
        if (!doctorRepository.existsById(id)) throw new NotFoundException("Doctor not found");
        doctorRepository.deleteById(id);
    }

    private DoctorResponse toResponse(Doctor d) {
        return new DoctorResponse(
                d.getId(),
                d.getUser().getId(),
                d.getDepartment().getId(),
                d.getLicenseNumber(),
                d.getSpecialization(),
//                d.getPhone(),
//                d.getEmail(),
//                d.getRoomNumber(),
//                d.getWorkingHours(),
//                d.getBiography(),
                d.getActive()
        );
    }
}