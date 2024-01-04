package com.nitsoft.login.board.domain.dto;

import com.nitsoft.login.board.domain.entity.Board;
import com.nitsoft.login.board.domain.response.BoardSearchResponse;
import lombok.Builder;

@Builder
public record BoardThumbnailDto(
    Long id,
    String title,
    String nickname
) {
    public static BoardThumbnailDto fromEntity(Board board){
        return BoardThumbnailDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .nickname(board.getMember().getNickname())
                .build();
    }

}
