package com.example.neuralfit.user.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ConnectionTryDto {
    @Size(min = 6, max = 6, message = "초대 코드는 6자입니다")
    private final String key;
}
