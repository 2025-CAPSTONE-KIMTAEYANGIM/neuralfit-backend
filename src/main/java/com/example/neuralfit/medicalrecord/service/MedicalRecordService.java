package com.example.neuralfit.medicalrecord.service;

import com.example.neuralfit.common.code.UserRole;
import com.example.neuralfit.common.exception.ForbiddenException;
import com.example.neuralfit.medicalrecord.dto.AddMedicalRecordRequest;
import com.example.neuralfit.medicalrecord.dto.MedicalRecordDto;
import com.example.neuralfit.medicalrecord.entity.MedicalRecord;
import com.example.neuralfit.medicalrecord.repository.MedicalRecordRepository;
import com.example.neuralfit.user.entity.AppUser;
import com.example.neuralfit.user.entity.UserConnection;
import com.example.neuralfit.user.repository.PatientRepository;
import com.example.neuralfit.user.repository.TherapistRepository;
import com.example.neuralfit.user.repository.UserConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final UserConnectionRepository userConnectionRepository;
    private final PatientRepository patientRepository;
    private final TherapistRepository therapistRepository;

    @Transactional(readOnly = true)
    public List<MedicalRecordDto> getMedicalRecords(int patientId, int year, int month) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (appUser.getUserRole() == UserRole.PATIENT) {
            if (patientId != appUser.getId()) {
                throw new ForbiddenException("접근 권한이 없습니다.");
            }
        } else {
            if (!userConnectionRepository.existsByTherapist_IdAndPatient_Id(appUser.getId(), patientId)) {
                throw new ForbiddenException("접근 권한이 없습니다.");
            }
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        return medicalRecordRepository.findByUserConnection_Patient_IdAndConsultationDateBetween(patientId, startDate, endDate)
                .stream().map(MedicalRecordDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public void addMedicalRecordRequest(int patientId, AddMedicalRecordRequest request) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appUser.getUserRole() != UserRole.THERAPIST) {
            throw new ForbiddenException("접근 권한이 없습니다.");
        }

        UserConnection userConnection = userConnectionRepository.findByTherapist_IdAndPatient_Id(appUser.getId(), patientId)
                .orElseThrow(() -> new ForbiddenException("접근 권한이 없습니다."));

        medicalRecordRepository.save(MedicalRecord.builder()
                .consultationDate(request.getConsultationDate())
                .consultationType(request.getConsultationType())
                .faq(request.getFaq())
                .mmse(request.getMmse())
                .moca(request.getMoca())
                .ptau(request.getPtau())
                .abeta(request.getAbeta())
                .adas13(request.getAdas13())
                .diagnosis(request.getDiagnosis())
                .ecogPtMem(request.getEcogPtMem())
                .ecogPtTotal(request.getEcogPtTotal())
                .description(request.getDescription())
                .ldelTotal(request.getLdelTotal())
                .patientComment(request.getPatientComment())
                .userConnection(userConnection)
                .build());
    }
}
