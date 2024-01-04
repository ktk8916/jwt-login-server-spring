package com.nitsoft.login.auth.service;

import com.nitsoft.login.auth.domain.entity.RefreshTokenLog;
import com.nitsoft.login.auth.domain.request.LoginRequest;
import com.nitsoft.login.auth.domain.request.SignupRequest;
import com.nitsoft.login.auth.domain.response.TokenResponse;
import com.nitsoft.login.auth.repository.RefreshTokenLogRepository;
import com.nitsoft.login.global.exception.ApiException;
import com.nitsoft.login.global.jwt.JwtService;
import com.nitsoft.login.member.domain.entity.Member;
import com.nitsoft.login.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.nitsoft.login.global.exception.ExceptionType.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenLogRepository refreshTokenLogRepository;
    private final JwtService jwtService;

    @Transactional
    public TokenResponse signup(SignupRequest request){
        Optional<Member> optionalMember = memberRepository.findByEmail(request.email());

        if(optionalMember.isPresent()){
            throw new ApiException(USERNAME_ALREADY_REGISTERED);
        }

        Member member = Member.builder()
                .email(request.email())
                .password(request.password())
                .nickname(request.nickname())
                .build();

        Member savedMember = memberRepository.save(member);

        return issueToken(savedMember);
    }

    @Transactional
    public TokenResponse login(LoginRequest request){
        Member member = memberRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));

        return issueToken(member);
    }

    @Transactional
    public TokenResponse renew(String refreshToken) {
        RefreshTokenLog refreshTokenLog = refreshTokenLogRepository.findByRefreshTokenFetchMember(refreshToken)
                .orElseThrow(() -> new ApiException(INVALID_REFRESH_TOKEN));

        Member member = refreshTokenLog.getMember();

        String newRefreshToken = jwtService.generateRefreshToken(member);
        String newAccessToken = jwtService.generateAccessToken(member);

        refreshTokenLog.updateRefreshToken(newRefreshToken);

        return TokenResponse.of(newAccessToken, newRefreshToken);
    }

    private TokenResponse issueToken(Member member){
        String accessToken = jwtService.generateAccessToken(member);
        String refreshToken = jwtService.generateRefreshToken(member);

        RefreshTokenLog tokenLog = RefreshTokenLog.builder()
                .member(member)
                .refreshToken(refreshToken)
                .build();

        refreshTokenLogRepository.save(tokenLog);

        return TokenResponse.of(accessToken, refreshToken);
    }
}
