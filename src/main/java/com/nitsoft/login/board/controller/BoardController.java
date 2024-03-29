package com.nitsoft.login.board.controller;

import com.nitsoft.login.board.domain.dto.BoardThumbnailDto;
import com.nitsoft.login.board.domain.request.BoardRequest;
import com.nitsoft.login.board.domain.response.BoardDetailResponse;
import com.nitsoft.login.board.domain.response.BoardSearchResponse;
import com.nitsoft.login.board.service.BoardService;
import com.nitsoft.login.global.jwt.TokenInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public BoardSearchResponse searchByCondition(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "keyword", required = false) String keyword
    ){
        PageRequest pageRequest = PageRequest.of(page, size);
        return boardService.searchByCondition(keyword, pageRequest);
    }

    @GetMapping("/{boardId}")
    public BoardDetailResponse getById(@PathVariable long boardId){
        return boardService.getById(boardId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardThumbnailDto createBoard(
            @AuthenticationPrincipal TokenInfo tokenInfo,
            @RequestBody @Valid BoardRequest request
    ){
        return boardService.createBoard(tokenInfo.getId(), request);
    }

    @PutMapping("/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBoard(
            @AuthenticationPrincipal TokenInfo tokenInfo,
            @PathVariable long boardId,
            @RequestBody @Valid BoardRequest request
    ){
        boardService.updateBoard(boardId, tokenInfo.getId(), request);
    }

    @DeleteMapping("/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(
            @AuthenticationPrincipal TokenInfo tokenInfo,
            @PathVariable long boardId
    ){
            boardService.deleteBoard(boardId, tokenInfo.getId());
    }
}
