package com.nitsoft.login.member.domain.dto;

import com.nitsoft.login.member.domain.entity.Member;
import lombok.Builder;

@Builder
public record MemberDto(
        Long id,
        String nickname
) {
    public static MemberDto fromEntity(Member member){
        return MemberDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
