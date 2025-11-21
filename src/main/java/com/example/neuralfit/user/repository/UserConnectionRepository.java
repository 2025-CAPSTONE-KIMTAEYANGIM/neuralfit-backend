package com.example.neuralfit.user.repository;

import com.example.neuralfit.user.entity.Patient;
import com.example.neuralfit.user.entity.Therapist;
import com.example.neuralfit.user.entity.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnection, Integer> {
    Optional<UserConnection> findByTherapistAndPatient(Therapist therapist, Patient patient);

    @Query("SELECT uc FROM UserConnection uc " +
            "JOIN FETCH uc.patient p " +
            "JOIN FETCH p.appUser au " +
            "WHERE uc.therapist = :therapist")
    List<UserConnection> findByTherapistWithFetchJoin(@Param("therapist") Therapist therapist);
}
