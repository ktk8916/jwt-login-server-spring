package com.nitsoft.login.member.service;

import com.nitsoft.login.member.domain.response.MemberInformationResponse;
import com.nitsoft.login.member.repository.MemberMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMemoryRepository memberRepository;

    public List<MemberInformationResponse> getAllMembers(){
        return memberRepository.findAll().stream()
                .map(MemberInformationResponse::fromEntity)
                .toList();
    }
}
