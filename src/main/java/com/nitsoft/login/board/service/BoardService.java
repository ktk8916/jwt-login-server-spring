package com.nitsoft.login.board.service;

import com.nitsoft.login.board.domain.dto.BoardThumbnailDto;
import com.nitsoft.login.board.domain.entity.Board;
import com.nitsoft.login.board.domain.request.BoardRequest;
import com.nitsoft.login.board.domain.response.BoardDetailResponse;
import com.nitsoft.login.board.domain.response.BoardSearchResponse;
import com.nitsoft.login.board.repository.BoardRepository;
import com.nitsoft.login.global.exception.ApiException;
import com.nitsoft.login.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nitsoft.login.global.exception.ExceptionType.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardDetailResponse getById(long boardId) {
        Board board = findByIdFetchMember(boardId);
        return BoardDetailResponse.fromEntity(board);
    }

    public BoardSearchResponse searchByCondition(String keyword, Pageable pageable) {
        Page<BoardThumbnailDto> boards = boardRepository.findByCondition(keyword, pageable);
        return BoardSearchResponse.of(boards.getContent(), boards.getNumber(), boards.getSize(), boards.getTotalElements());
    }

    public void createBoard(long memberId, BoardRequest request) {
        Board board = Board.builder()
                .title(request.content())
                .content(request.content())
                .member(Member.fromId(memberId))
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(long boardId, long memberId, BoardRequest request) {
        Board board = findByIdFetchMember(boardId);

        if(!checkBoardOwner(board, memberId)){
            throw new ApiException(UNAUTHORIZED_CONTENT_OWNER);
        }

        board.update(request.title(), request.content());
    }

    @Transactional
    public void deleteBoard(long boardId, long memberId) {
        Board board = findById(boardId);

        if(!checkBoardOwner(board, memberId)){
            throw new ApiException(UNAUTHORIZED_CONTENT_OWNER);
        }

        board.delete();
    }

    private Board findById(long boardId){
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiException(CONTENT_NOT_FOUND));
    }

    private Board findByIdFetchMember(long boardId){
        return boardRepository.findByIdFetchMember(boardId)
                .orElseThrow(() -> new ApiException(CONTENT_NOT_FOUND));
    }

    private boolean checkBoardOwner(Board board, long memberId){
        return board.getMember().getId().equals(memberId);
    }
}
