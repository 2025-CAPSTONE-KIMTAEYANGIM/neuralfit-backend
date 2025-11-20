package com.example.neuralfit.user.dto;

import com.example.neuralfit.common.code.TherapistType;
import com.example.neuralfit.user.entity.Therapist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TherapistInfoDto {
    private final TherapistType therapistType;
    private final String organization;

    public TherapistInfoDto fromEntity(Therapist therapist) {
        return TherapistInfoDto.builder()
                .organization(therapist.getOrganization())
                .therapistType(therapist.getTherapistType())
                .build();
    }
}
