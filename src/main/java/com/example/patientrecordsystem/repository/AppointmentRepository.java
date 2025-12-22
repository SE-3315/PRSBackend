package com.example.patientrecordsystem.repository;

import com.example.patientrecordsystem.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Spring Data JPA repository for {@link com.example.patientrecordsystem.entity.Appointment} entities.
 */
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {}
