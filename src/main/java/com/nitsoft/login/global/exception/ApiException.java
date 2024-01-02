package com.nitsoft.login.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public ApiException(ExceptionType e) {
        this.httpStatus = e.getHttpStatus();
        this.message = e.getMessage();
    }
}
