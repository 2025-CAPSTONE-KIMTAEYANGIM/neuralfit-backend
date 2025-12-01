package com.example.neuralfit.medicalrecord.dto;

import com.example.neuralfit.medicalrecord.type.ConsultationType;
import com.example.neuralfit.medicalrecord.type.Diagnosis;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMedicalRecordRequest {
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
}
