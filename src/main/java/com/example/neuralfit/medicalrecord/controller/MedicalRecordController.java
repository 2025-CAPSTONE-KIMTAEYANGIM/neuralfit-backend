package com.example.neuralfit.medicalrecord.controller;

import com.example.neuralfit.medicalrecord.dto.AddMedicalRecordRequest;
import com.example.neuralfit.medicalrecord.dto.MedicalRecordDto;
import com.example.neuralfit.medicalrecord.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @GetMapping("")
    public ResponseEntity<List<MedicalRecordDto>> getMedicalRecord(
            @RequestParam int patient_id,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return ResponseEntity.ok(medicalRecordService.getMedicalRecords(patient_id, year, month));
    }

    @PostMapping("{patient_id}")
    public ResponseEntity<Void> addMedicalRecord(
            @RequestBody @Valid AddMedicalRecordRequest request,
            @PathVariable int patient_id
    ) {
        medicalRecordService.addMedicalRecordRequest(patient_id, request);

        return ResponseEntity.ok().build();
    }
}
