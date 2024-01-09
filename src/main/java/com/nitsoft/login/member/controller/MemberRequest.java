package com.nitsoft.login.member.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record MemberRequest(
        @NotBlank(message = "nickname is required")
        @Length(max = 20, message = "nickname must be less than 20 characters")
        String nickname
) {
}
