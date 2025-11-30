package com.example.neuralfit.user.entity;

import com.example.neuralfit.common.code.TherapistType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "therapists")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Therapist {
    @Id
    private int id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private AppUser appUser;

    @Column(name = "therapist_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TherapistType therapistType;

    @Column(name = "organization", nullable = false)
    private String organization;
}