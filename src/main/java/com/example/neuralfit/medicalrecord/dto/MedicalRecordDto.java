package com.example.neuralfit.medicalrecord.dto;

import com.example.neuralfit.medicalrecord.entity.MedicalRecord;
import com.example.neuralfit.medicalrecord.type.ConsultationType;
import com.example.neuralfit.medicalrecord.type.Diagnosis;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class MedicalRecordDto {
    private Integer id;
    private Integer patientId;
    private Integer therapistId;
    private ConsultationType consultationType;
    private String description;
    private LocalDate consultationDate;
    private Diagnosis diagnosis;
    private String patientComment;
    private Integer moca;
    private Integer mmse;
    private Integer faq;
    private Integer ldelTotal;
    private Integer adas13;
    private Double abeta;
    private Double ptau;
    private Double ecogPtMem;
    private Double ecogPtTotal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MedicalRecordDto fromEntity(MedicalRecord medicalRecord) {
        return MedicalRecordDto.builder()
                .id(medicalRecord.getId())
                .consultationType(medicalRecord.getConsultationType())
                .patientId(medicalRecord.getUserConnection().getPatient().getId())
                .patientComment(medicalRecord.getPatientComment())
                .therapistId(medicalRecord.getUserConnection().getTherapist().getId())
                .description(medicalRecord.getDescription())
                .consultationDate(medicalRecord.getConsultationDate())
                .diagnosis(medicalRecord.getDiagnosis())
                .moca(medicalRecord.getMoca())
                .mmse(medicalRecord.getMmse())
                .faq(medicalRecord.getFaq())
                .ldelTotal(medicalRecord.getLdelTotal())
                .adas13(medicalRecord.getAdas13())
                .abeta(medicalRecord.getAbeta())
                .ptau(medicalRecord.getPtau())
                .ecogPtMem(medicalRecord.getEcogPtMem())
                .ecogPtTotal(medicalRecord.getEcogPtTotal())
                .createdAt(medicalRecord.getCreatedAt())
                .updatedAt(medicalRecord.getUpdatedAt())
                .build();
    }
}
