package com.nitsoft.login.member.service;

import com.nitsoft.login.global.exception.ApiException;
import com.nitsoft.login.member.controller.MemberRequest;
import com.nitsoft.login.member.domain.entity.Member;
import com.nitsoft.login.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nitsoft.login.global.exception.ExceptionType.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void editMember(Long memberId, MemberRequest request) {
        Member member = findById(memberId);
        member.editMember(request.nickname());
    }

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));
    }
}
