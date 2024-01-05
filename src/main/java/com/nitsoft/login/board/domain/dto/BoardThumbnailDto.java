package com.nitsoft.login.board.domain.dto;

import com.nitsoft.login.board.domain.entity.Board;
import com.nitsoft.login.member.domain.dto.MemberDto;
import lombok.Builder;

@Builder
public record BoardThumbnailDto(
    Long id,
    String title,
    MemberDto member
) {
    public static BoardThumbnailDto fromEntity(Board board){
        return BoardThumbnailDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .member(MemberDto.fromEntity(board.getMember()))
                .build();
    }
}
