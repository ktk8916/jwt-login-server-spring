package com.nitsoft.login.member.controller;

import com.nitsoft.login.global.jwt.TokenInfo;
import com.nitsoft.login.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editMember(
            @AuthenticationPrincipal TokenInfo tokenInfo,
            @RequestBody @Valid MemberRequest request
    ){
        memberService.editMember(tokenInfo.getId(), request);
    }
}
