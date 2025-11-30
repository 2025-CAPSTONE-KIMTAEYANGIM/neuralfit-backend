package com.example.neuralfit.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_connection", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"therapist_id", "patient_id"})
})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "therapist_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Therapist therapist;

    @JoinColumn(name = "patient_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
