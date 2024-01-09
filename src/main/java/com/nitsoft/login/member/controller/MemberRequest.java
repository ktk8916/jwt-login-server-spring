package com.nitsoft.login.member.controller;

import lombok.Builder;

@Builder
public record MemberRequest(
        String nickname
) {
}
