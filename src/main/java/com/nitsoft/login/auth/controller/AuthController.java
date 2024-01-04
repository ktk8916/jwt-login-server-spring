package com.nitsoft.login.auth.controller;

import com.nitsoft.login.auth.domain.request.LoginRequest;
import com.nitsoft.login.auth.domain.request.SignupRequest;
import com.nitsoft.login.auth.domain.response.TokenResponse;
import com.nitsoft.login.auth.service.AuthService;
import com.nitsoft.login.global.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        // TODO : 일단 rdb로 logout구현..?
    }

    @GetMapping("/renew")
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
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int)JwtService.REFRESH_TOKEN_EXPIRATION_TIME / 1000);
        servletResponse.addCookie(cookie);
    }
}
