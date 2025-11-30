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
    private int moca;
    private int mmse;
    private int faq;
    private int ldelTotal;
    private int adas13;
    private double abeta;
    private double ptau;
    private double ecogPtMem;
    private double ecogPtTotal;
}
