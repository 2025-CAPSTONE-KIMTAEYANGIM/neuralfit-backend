package com.example.neuralfit.user.controller;

import com.example.neuralfit.user.dto.AppUserInfoDto;
import com.example.neuralfit.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<AppUserInfoDto> getMe(){
        return ResponseEntity.ok(userService.getMe());
    }
}
