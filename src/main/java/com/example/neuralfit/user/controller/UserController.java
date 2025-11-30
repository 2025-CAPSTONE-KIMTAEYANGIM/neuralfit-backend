package com.example.neuralfit.user.controller;

import com.example.neuralfit.user.dto.*;
import com.example.neuralfit.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<AppUserInfoDto> getMe() {
        return ResponseEntity.ok(userService.getMe());
    }

    @PostMapping("/connection/generate")
    public ResponseEntity<ConnectionKeyDto> generateConnectionKey() {
        return ResponseEntity.ok(userService.generateConnectionKey());
    }

    @GetMapping("/my-patients")
    public ResponseEntity<List<PatientInfoDto>> getMyPatients() {
        return ResponseEntity.ok(userService.getMyPatients());
    }

    @PostMapping("/connection/try")
    public ResponseEntity<Void> tryConnection(
            @RequestBody @Valid ConnectionTryDto connectionTryDto
    ) {
        userService.tryConnection(connectionTryDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/connection/{patient_id}")
    public ResponseEntity<Void> deleteConnection(
            @PathVariable Integer patient_id
    ) {
        userService.deleteConnection(patient_id);

        return ResponseEntity.ok().build();
    }
}
