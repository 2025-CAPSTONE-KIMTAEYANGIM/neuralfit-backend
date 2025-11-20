package com.example.neuralfit.auth;

import com.example.neuralfit.common.code.UserRole;
import com.example.neuralfit.user.entity.AppUser;
import com.example.neuralfit.user.entity.Patient;
import com.example.neuralfit.user.entity.Therapist;
import com.example.neuralfit.common.exception.ConflictException;
import com.example.neuralfit.user.repository.AppUserRepository;
import com.example.neuralfit.user.repository.PatientRepository;
import com.example.neuralfit.user.repository.TherapistRepository;
import com.example.neuralfit.common.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AppUserRepository appUserRepository;
    private final PatientRepository patientRepository;
    private final TherapistRepository therapistRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        return LoginResponse.builder()
                .accessToken(jwtUtil.generateAccessToken(auth))
                .refreshToken(jwtUtil.generateRefreshToken(auth))
                .build();
    }

    @Transactional
    public void patientSignUp(PatientSignUpRequest signUpRequest) {
        appUserRepository.findByEmail(signUpRequest.getEmail())
                .ifPresent(appUser -> {
                    throw new ConflictException("중복되는 이메일입니다");
                });

        AppUser appUser = appUserRepository.save(AppUser.builder()
                .email(signUpRequest.getEmail())
                .name(signUpRequest.getName())
                .userRole(UserRole.PATIENT)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build()
        );

        patientRepository.save(Patient.builder()
                .appUser(appUser)
                .birthdate(signUpRequest.getBirthDate())
                .gender(signUpRequest.getGender())
                .build());
    }

    @Transactional
    public void therapistSignUp(TherapistSignUpRequest signUpRequest) {
        appUserRepository.findByEmail(signUpRequest.getEmail())
                .ifPresent(appUser -> {
                    throw new ConflictException("중복되는 이메일입니다");
                });

        AppUser appUser = appUserRepository.save(AppUser.builder()
                .email(signUpRequest.getEmail())
                .name(signUpRequest.getName())
                .userRole(UserRole.THERAPIST)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build()
        );

        therapistRepository.save(Therapist.builder()
                .appUser(appUser)
                .organization(signUpRequest.getOrganization())
                .therapistType(signUpRequest.getTherapistType())
                .build());
    }
}
