package com.nitsoft.login.auth.domain.dto;

import com.nitsoft.login.member.domain.dto.MemberDto;
import com.nitsoft.login.member.domain.entity.Member;
import lombok.Builder;

@Builder
public record LoginResponse(
    String accessToken,
    String refreshToken,
    MemberDto member
) {
    public static LoginResponse of(String accessToken, String refreshToken, Member member){
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .member(MemberDto.fromEntity(member))
                .build();
    }
}
