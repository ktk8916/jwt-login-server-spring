package com.nitsoft.login.board.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BoardRequest(
        @NotBlank(message = "title is required")
        String title,
        @NotBlank(message = "content is required")
        String content
) {
}
