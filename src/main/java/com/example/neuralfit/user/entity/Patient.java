package com.example.neuralfit.user.entity;

import com.example.neuralfit.common.code.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {
    @Id
    private int id;

    @MapsId
    @JoinColumn(name = "id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AppUser appUser;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthDate;
}
