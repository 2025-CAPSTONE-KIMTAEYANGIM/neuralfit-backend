package com.example.neuralfit.common.dto;

import com.example.neuralfit.common.code.Gender;
import com.example.neuralfit.common.entity.AppUser;
import com.example.neuralfit.common.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PatientInfoDto {
    private final Gender gender;
    private final LocalDate birthdate;

    public PatientInfoDto fromEntity(Patient patient) {
        return PatientInfoDto.builder()
                .gender(patient.getGender())
                .birthdate(patient.getBirthdate())
                .build();
    }
}
