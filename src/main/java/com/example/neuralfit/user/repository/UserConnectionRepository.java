package com.example.neuralfit.user.repository;

import com.example.neuralfit.user.entity.Patient;
import com.example.neuralfit.user.entity.Therapist;
import com.example.neuralfit.user.entity.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnection, Integer> {
    Optional<UserConnection> findByTherapistAndPatient(Therapist therapist, Patient patient);
}
