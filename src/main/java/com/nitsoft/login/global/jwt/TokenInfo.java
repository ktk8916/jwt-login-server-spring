package com.nitsoft.login.global.jwt;

import lombok.Builder;

public class TokenInfo {

    private Long id;
    private String username;
    private String nickname;

    @Builder
    public TokenInfo(Long id, String username, String nickname) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
    }
}
