package com.nitsoft.login.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionType {

    // auth
    MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "member not found"),
    EMAIL_ALREADY_REGISTERED(HttpStatus.CONFLICT, "email already registered"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "invalid password"),

    // jwt
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "invalid refresh token"),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "access token expired"),
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "invalid access token"),

    //board
    UNAUTHORIZED_CONTENT_OWNER(HttpStatus.FORBIDDEN, "unauthorized content owner"),
    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "content not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
