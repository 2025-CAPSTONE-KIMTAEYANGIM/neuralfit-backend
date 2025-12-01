package com.example.neuralfit.medicalrecord.entity;

import com.example.neuralfit.medicalrecord.type.ConsultationType;
import com.example.neuralfit.medicalrecord.type.Diagnosis;
import com.example.neuralfit.user.entity.UserConnection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_record", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"connection_id", "consultation_date"})
})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "connection_id", nullable = false)
    private UserConnection userConnection;

    @Column(name = "consultation_type")
    @Enumerated(EnumType.STRING)
    private ConsultationType consultationType;

    @Column(name = "description")
    private String description;

    @Column(name = "patient_comment")
    private String patientComment;

    @Column(name = "consultation_date", nullable = false)
    private LocalDate consultationDate;

    @Column(name = "dx", nullable = false)
    @Enumerated(EnumType.STRING)
    private Diagnosis diagnosis;

    @Column(name = "moca")
    private int moca;

    @Column(name = "mmse")
    private int mmse;

    @Column(name = "faq")
    private int faq;

    @Column(name = "ldel_total")
    private int ldelTotal;

    @Column(name = "adas13")
    private int adas13;

    @Column(name = "abeta")
    private double abeta;

    @Column(name = "ptau")
    private double ptau;

    @Column(name = "ecog_pt_mem")
    private double ecogPtMem;

    @Column(name = "ecog_pt_total")
    private double ecogPtTotal;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
