package com.nitsoft.login.member.controller;

import com.nitsoft.login.member.domain.response.MemberInformationResponse;
import com.nitsoft.login.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public List<MemberInformationResponse> getAllMembers(){
        return memberService.getAllMembers();
    }
}
