package com.nitsoft.login.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionType {
    MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "member not found"),
    USERNAME_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "username already registered")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
