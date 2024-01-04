package com.nitsoft.login.board.domain.response;

import com.nitsoft.login.board.domain.entity.Board;
import com.nitsoft.login.member.domain.dto.MemberDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BoardDetailResponse(
        Long id,
        String title,
        String content,
        MemberDto member,
        LocalDateTime createdAt
) {
    public static BoardDetailResponse fromEntity(Board board){
        return BoardDetailResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .member(MemberDto.fromEntity(board.getMember()))
                .createdAt(board.getCreatedAt())
                .build();
    }
}
