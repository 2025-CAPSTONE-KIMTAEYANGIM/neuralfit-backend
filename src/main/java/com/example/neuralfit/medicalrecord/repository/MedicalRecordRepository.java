package com.example.neuralfit.medicalrecord.repository;

import com.example.neuralfit.medicalrecord.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
    List<MedicalRecord> findByUserConnection_Patient_IdAndConsultationDateBetween(
            int patientId, LocalDate start, LocalDate end);
}
