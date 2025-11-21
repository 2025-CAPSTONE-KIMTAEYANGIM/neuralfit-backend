package com.example.neuralfit.user.service;

import com.example.neuralfit.common.code.UserRole;
import com.example.neuralfit.common.exception.ConflictException;
import com.example.neuralfit.common.exception.ForbiddenException;
import com.example.neuralfit.user.dto.*;
import com.example.neuralfit.user.entity.AppUser;
import com.example.neuralfit.user.entity.Patient;
import com.example.neuralfit.user.entity.Therapist;
import com.example.neuralfit.user.entity.UserConnection;
import com.example.neuralfit.user.repository.AppUserRepository;
import com.example.neuralfit.user.repository.PatientRepository;
import com.example.neuralfit.user.repository.TherapistRepository;
import com.example.neuralfit.user.repository.UserConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AppUserRepository appUserRepository;
    private final PatientRepository patientRepository;
    private final TherapistRepository therapistRepository;
    private final UserConnectionRepository userConnectionRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional(readOnly = true)
    public AppUserInfoDto getMe() {
        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(currentUser.getUserRole() == UserRole.PATIENT){
            Patient patient = patientRepository.findById(currentUser.getId())
                    .orElseThrow(() -> new NoSuchElementException("해당 환자를 찾을 수 없습니다."));

            return PatientInfoDto.fromEntity(currentUser, patient);
        } else {
            Therapist therapist = therapistRepository.findById(currentUser.getId())
                    .orElseThrow(() -> new NoSuchElementException("해당 의료진을 찾을 수 없습니다."));

            return TherapistInfoDto.fromEntity(currentUser, therapist);
        }
    }

    @Transactional(readOnly = true)
    public ConnectionKeyDto generateConnectionKey() {
        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getUserRole() != UserRole.THERAPIST) {
            throw new ForbiddenException("권한이 없습니다.");
        }

        String key = UUID.randomUUID().toString().substring(0, 6);

        while (redisTemplate.hasKey(key)) {
            key = UUID.randomUUID().toString().substring(0, 6);
        }

        redisTemplate.opsForValue().set(key, currentUser.getId().toString(), Duration.ofMinutes(5));

        return new ConnectionKeyDto(key);
    }

    @Transactional(readOnly = true)
    public void tryConnection(ConnectionTryDto connectionTryDto) {
        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getUserRole() != UserRole.PATIENT) {
            throw new ForbiddenException("권한이 없습니다.");
        }

        if(!redisTemplate.hasKey(connectionTryDto.getKey())) {
            throw new IllegalArgumentException("올바르지 않은 코드입니다.");
        }

        Integer therapistId = Integer.parseInt(redisTemplate.opsForValue().get(connectionTryDto.getKey()));
        Therapist therapist = therapistRepository.findById(therapistId)
                .orElseThrow(() -> new NoSuchElementException("해당 의료진을 찾을 수 없습니다."));
        Patient patient = patientRepository.findById(currentUser.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 환자를 찾을 수 없습니다."));

        userConnectionRepository.findByTherapistAndPatient(therapist, patient)
                        .ifPresent(conn -> {throw new ConflictException("이미 연결되어 있습니다.");});

        userConnectionRepository.save(
                UserConnection.builder()
                        .patient(patient)
                        .therapist(therapist)
                        .build()
        );
    }
}
