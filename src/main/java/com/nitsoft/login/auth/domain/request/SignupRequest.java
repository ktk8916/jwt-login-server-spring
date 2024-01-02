package com.nitsoft.login.auth.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SignupRequest(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "password is required")
        String password,
        @NotBlank(message = "nickname is required")
        String nickname
) {
}
