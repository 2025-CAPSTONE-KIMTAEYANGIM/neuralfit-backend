package com.example.neuralfit.common.service;

import com.example.neuralfit.common.dto.AppUserInfoDto;
import com.example.neuralfit.common.entity.AppUser;
import com.example.neuralfit.common.repository.AppUserRepository;
import com.example.neuralfit.common.repository.PatientRepository;
import com.example.neuralfit.common.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AppUserRepository appUserRepository;
    private final PatientRepository patientRepository;
    private final TherapistRepository therapistRepository;

    public AppUserInfoDto getMe() {
        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUser user = appUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return AppUserInfoDto.fromEntity(user);
    }
}
