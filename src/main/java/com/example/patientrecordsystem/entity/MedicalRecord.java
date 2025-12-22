package com.example.patientrecordsystem.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.util.UUID;
/**
 * JPA entity representing a patient's medical record entry.
 *
 * <p>Holds record type, description, attachments and references to patient and doctor.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medical_records")
public class MedicalRecord {
    public MedicalRecord(UUID id, Patient patient, Doctor doctor, String recordType, String description, String attachments, Instant createdAt) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.recordType = recordType;
        this.description = description;
        this.attachments = attachments;
        this.createdAt = createdAt;
    }

    @Id @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false) @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(optional = false) @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private String recordType;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String attachments;

    @CreationTimestamp
    private Instant createdAt;

    public MedicalRecord() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
