package com.nitsoft.login.board.domain.response;

import com.nitsoft.login.board.domain.dto.BoardThumbnailDto;
import lombok.Builder;

import java.util.List;

@Builder
public record BoardSearchResponse(
        List<BoardThumbnailDto> boards,
        int page,
        int size,
        long totalSize
) {
    public static BoardSearchResponse of(List<BoardThumbnailDto> boards, int page, int size, long totalSize){
        return BoardSearchResponse.builder()
                .boards(boards)
                .page(page)
                .size(size)
                .totalSize(totalSize)
                .build();
    }
}
