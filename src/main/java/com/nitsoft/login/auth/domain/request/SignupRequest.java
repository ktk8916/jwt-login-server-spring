package com.nitsoft.login.auth.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record SignupRequest(
        @NotBlank(message = "email is required")
        @Email(message = "invalid email form")
        String email,
        @NotBlank(message = "password is required")
        String password,
        @NotBlank(message = "nickname is required")
        @Length(max = 20, message = "nickname must be less than 20 characters")
        String nickname
) {
}
