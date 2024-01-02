package com.nitsoft.login.auth.service;

import com.nitsoft.login.auth.domain.request.LoginRequest;
import com.nitsoft.login.auth.domain.request.SignupRequest;
import com.nitsoft.login.auth.domain.response.LoginResponse;
import com.nitsoft.login.global.exception.ApiException;
import com.nitsoft.login.global.jwt.JwtService;
import com.nitsoft.login.member.domain.entity.Member;
import com.nitsoft.login.member.repository.MemberMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.nitsoft.login.global.exception.ExceptionType.MEMBER_NOT_FOUND;
import static com.nitsoft.login.global.exception.ExceptionType.USERNAME_ALREADY_REGISTERED;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberMemoryRepository memberRepository;
    private final JwtService jwtService;

    public LoginResponse signup(SignupRequest request){
        Optional<Member> optionalMember = memberRepository.findByUsername(request.username());

        if(optionalMember.isPresent()){
            throw new ApiException(USERNAME_ALREADY_REGISTERED);
        }

        Member member = Member.builder()
                .username(request.username())
                .password(request.password())
                .nickname(request.nickname())
                .build();

        Member savedMember = memberRepository.save(member);

        return getLoginResponseByMember(savedMember);
    }

    public LoginResponse login(LoginRequest request){
        Member member = memberRepository.findByUsernameAndPassword(request.username(), request.password())
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));

        return getLoginResponseByMember(member);
    }

    private LoginResponse getLoginResponseByMember(Member member){
        String token = jwtService.generateToken(member);
        return LoginResponse.of(token);
    }
}
