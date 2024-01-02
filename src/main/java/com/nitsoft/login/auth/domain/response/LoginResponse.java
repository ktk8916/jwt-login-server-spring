package com.nitsoft.login.auth.domain.response;

import lombok.Builder;

@Builder
public record LoginResponse(
    String accessToken
) {
    public static LoginResponse of(String accessToken){
        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
