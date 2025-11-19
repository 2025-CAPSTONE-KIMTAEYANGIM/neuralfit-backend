package com.example.neuralfit.auth;

import com.example.neuralfit.common.code.TherapistType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TherapistSignUpRequest {
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Length(min = 8, max = 32, message = "비밀번호는 최소 8자, 최대 32자 입력 가능합니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "영문자와 숫자 최소 1개씩 포함되어야 합니다")
    private String password;
    @NotBlank(message = "이름을 입력해주세요")
    @Length(min = 1, max = 16, message = "이름은 최소 1자, 최대 16자 입력 가능합니다")
    private String name;
    @NotBlank(message = "소속기관을 입력해주세요")
    private String organization;
    @NotNull(message = "의료진 유형을 입력해주세요")
    private TherapistType therapistType;
}
