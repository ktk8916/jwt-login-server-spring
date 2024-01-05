package com.nitsoft.login.board.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record BoardRequest(
        @NotBlank(message = "title is required")
        @Length(max = 200, message = "title must be less than 200 characters")
        String title,
        @NotBlank(message = "content is required")
        String content
) {
}
