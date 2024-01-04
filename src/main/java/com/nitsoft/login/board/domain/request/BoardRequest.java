package com.nitsoft.login.board.domain.request;

import lombok.Builder;

@Builder
public record BoardRequest(
        String title,
        String content
) {
}
