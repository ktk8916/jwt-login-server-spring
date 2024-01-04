package com.nitsoft.login.board.repository;

import com.nitsoft.login.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {

    @Query("select b " +
            "from Board b " +
            "inner join fetch b.member " +
            "where b.id = :boardId " +
            "and b.deletedAt is null")
    Optional<Board> findNotDeletedBoardByIdFetchMember(Long boardId);
}
