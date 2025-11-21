package com.example.neuralfit.user.dto;

import com.example.neuralfit.common.code.Gender;
import com.example.neuralfit.user.entity.AppUser;
import com.example.neuralfit.user.entity.Patient;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
public class PatientInfoDto extends AppUserInfoDto {
    private final Gender gender;
    private final LocalDate birthdate;

    public static PatientInfoDto fromEntity(AppUser appUser, Patient patient) {
        return PatientInfoDto.builder()
                .id(appUser.getId())
                .email(appUser.getEmail())
                .name(appUser.getName())
                .userRole(appUser.getUserRole())
                .createdAt(appUser.getCreatedAt())
                .updatedAt(appUser.getUpdatedAt())
                .gender(patient.getGender())
                .birthdate(patient.getBirthdate())
                .build();
    }
}
