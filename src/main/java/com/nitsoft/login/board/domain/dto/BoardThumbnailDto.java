package com.nitsoft.login.board.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BoardThumbnailDto {
    private Long id;
    private String title;
    private String nickname;

    @QueryProjection
    public BoardThumbnailDto(Long id, String title, String nickname) {
        this.id = id;
        this.title = title;
        this.nickname = nickname;
    }
}
