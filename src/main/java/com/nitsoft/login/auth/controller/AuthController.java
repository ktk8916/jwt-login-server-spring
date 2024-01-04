package com.nitsoft.login.auth.controller;

import com.nitsoft.login.auth.domain.request.LoginRequest;
import com.nitsoft.login.auth.domain.request.SignupRequest;
import com.nitsoft.login.auth.domain.response.TokenResponse;
import com.nitsoft.login.auth.service.AuthService;
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
    public TokenResponse signup(@RequestBody @Valid SignupRequest request){
        return authService.signup(request);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest request){
        return authService.login(request);
    }

    @GetMapping("/renew")
    public TokenResponse renew(){
        return authService.renew("aaa");
    }
}
