package com.example.neuralfit.user.dto;

import com.example.neuralfit.common.code.TherapistType;
import com.example.neuralfit.user.entity.AppUser;
import com.example.neuralfit.user.entity.Therapist;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class TherapistInfoDto extends AppUserInfoDto {
    private final TherapistType therapistType;
    private final String organization;

    public static TherapistInfoDto fromEntity(AppUser appUser, Therapist therapist) {
        return TherapistInfoDto.builder()
                .id(appUser.getId())
                .email(appUser.getEmail())
                .name(appUser.getName())
                .userRole(appUser.getUserRole())
                .createdAt(appUser.getCreatedAt())
                .updatedAt(appUser.getUpdatedAt())
                .organization(therapist.getOrganization())
                .therapistType(therapist.getTherapistType())
                .build();
    }
}
