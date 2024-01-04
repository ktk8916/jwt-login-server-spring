package com.nitsoft.login.auth.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SignupRequest(
        @NotBlank(message = "email is required")
        String email,
        @NotBlank(message = "password is required")
        String password,
        @NotBlank(message = "nickname is required")
        String nickname
) {
}
