package com.nitsoft.login.member.domain.response;

import com.nitsoft.login.member.domain.entity.Member;
import lombok.Builder;

@Builder
public record MemberInformationResponse(
        Long id,
        String username,
        String nickname
) {
    public static MemberInformationResponse fromEntity(Member member){
        return MemberInformationResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .build();
    }
}
