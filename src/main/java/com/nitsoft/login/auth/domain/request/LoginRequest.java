package com.nitsoft.login.auth.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginRequest(

        @NotBlank(message = "email is required")
        @Email(message = "invalid email form")
        String email,
        @NotBlank(message = "password is required")
        String password
) {
}
