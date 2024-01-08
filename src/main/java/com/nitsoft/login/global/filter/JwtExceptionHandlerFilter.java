package com.nitsoft.login.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nitsoft.login.global.exception.ExceptionResponse;
import com.nitsoft.login.global.exception.ExceptionType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            setExceptionResponse(response, ExceptionType.ACCESS_TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            setExceptionResponse(response, ExceptionType.INVALID_ACCESS_TOKEN);
        }
    }

    private void setExceptionResponse(HttpServletResponse response, ExceptionType type) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(type.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ExceptionResponse exceptionResponse = ExceptionResponse.of(type.getHttpStatus(), type.getMessage());
        objectMapper.writeValue(response.getWriter(), exceptionResponse);
    }
}
