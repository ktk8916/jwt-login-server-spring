package com.nitsoft.login.board.repository;

import com.nitsoft.login.board.domain.dto.BoardThumbnailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomBoardRepository {

    Page<BoardThumbnailDto> findByCondition(String keyword, Pageable Pageable);
}
