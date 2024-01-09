package com.nitsoft.login.auth.controller;

import com.nitsoft.login.auth.domain.request.LoginRequest;
import com.nitsoft.login.auth.domain.request.SignupRequest;
import com.nitsoft.login.auth.domain.response.TokenResponse;
import com.nitsoft.login.auth.service.AuthService;
import com.nitsoft.login.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse signup(
            HttpServletResponse servletResponse,
            @RequestBody @Valid SignupRequest request
    ){
        TokenResponse response = authService.signup(request);
        addRefreshTokenInCookie(servletResponse, response.refreshToken());
        return response;
    }

    @PostMapping("/login")
    public TokenResponse login(
            HttpServletResponse servletResponse,
            @RequestBody @Valid LoginRequest request
    ){
        TokenResponse response = authService.login(request);
        addRefreshTokenInCookie(servletResponse, response.refreshToken());
        return response;
    }

    @PostMapping("/logout")
    public void logout(){
    }

    @GetMapping("/renew")
    public TokenResponse renew(HttpServletRequest servletRequest){
        String authHeader = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        return authService.renew(token);
    }

    @GetMapping("/renew/cookie")
    public TokenResponse renew(
            HttpServletResponse servletResponse,
            @CookieValue String refreshToken
    ){
        TokenResponse response = authService.renew(refreshToken);
        addRefreshTokenInCookie(servletResponse, response.refreshToken());
        return response;
    }

    private void addRefreshTokenInCookie(
            HttpServletResponse servletResponse,
            String refreshToken
    ){
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .domain("localhost")
                .sameSite("")
                .secure(true)
                .httpOnly(true)
                .maxAge(JwtService.REFRESH_TOKEN_EXPIRATION_TIME)
                .build();
        servletResponse.addHeader("Set-Cookie", cookie.toString());

//        Cookie cookie = new Cookie("refreshToken", refreshToken);
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge((int)JwtService.REFRESH_TOKEN_EXPIRATION_TIME / 1000);
//        cookie.setPath("/");
//        servletResponse.addCookie(cookie);
    }
}
