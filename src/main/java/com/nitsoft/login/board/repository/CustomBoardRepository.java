package com.nitsoft.login.board.repository;

import com.nitsoft.login.board.domain.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomBoardRepository {

    Page<Board> findByCondition(String keyword, Pageable Pageable);
}
