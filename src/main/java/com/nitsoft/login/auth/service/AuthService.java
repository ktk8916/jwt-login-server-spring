package com.nitsoft.login.auth.service;

import com.nitsoft.login.auth.domain.entity.RefreshTokenLog;
import com.nitsoft.login.auth.domain.request.LoginRequest;
import com.nitsoft.login.auth.domain.request.SignupRequest;
import com.nitsoft.login.auth.domain.dto.LoginResponse;
import com.nitsoft.login.auth.repository.RefreshTokenLogRepository;
import com.nitsoft.login.global.exception.ApiException;
import com.nitsoft.login.global.jwt.JwtService;
import com.nitsoft.login.member.domain.entity.Member;
import com.nitsoft.login.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponse signup(SignupRequest request){
        checkEmailExist(request.email());

        Member member = Member.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .build();

        Member savedMember = memberRepository.save(member);

        return issueToken(savedMember);
    }


    @Transactional
    public LoginResponse login(LoginRequest request){
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(request.password(), member.getPassword())){
            throw new ApiException(INVALID_PASSWORD);
        }

        return issueToken(member);
    }

    public void checkEmailExist(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if(optionalMember.isPresent()){
            throw new ApiException(EMAIL_ALREADY_REGISTERED);
        }
    }

    @Transactional
    public LoginResponse renew(String refreshToken) {
        if(!jwtService.isValidToken(refreshToken)){
            throw new ApiException(INVALID_REFRESH_TOKEN);
        }

        RefreshTokenLog refreshTokenLog = refreshTokenLogRepository.findByRefreshTokenFetchMember(refreshToken)
                .orElseThrow(() -> new ApiException(INVALID_REFRESH_TOKEN));

        Member member = refreshTokenLog.getMember();

        String newRefreshToken = jwtService.generateRefreshToken(member);
        String newAccessToken = jwtService.generateAccessToken(member);

        refreshTokenLog.updateRefreshToken(newRefreshToken);

        return LoginResponse.of(newAccessToken, newRefreshToken, member);
    }

    private LoginResponse issueToken(Member member){
        String accessToken = jwtService.generateAccessToken(member);
        String refreshToken = jwtService.generateRefreshToken(member);

        refreshTokenLogRepository.findByMember(member)
                .ifPresentOrElse(
                        refreshTokenLog -> refreshTokenLog.updateRefreshToken(refreshToken),
                        () -> {
                            RefreshTokenLog tokenLog = RefreshTokenLog.builder()
                                    .member(member)
                                    .refreshToken(refreshToken)
                                    .build();
                            refreshTokenLogRepository.save(tokenLog);
                        });

        return LoginResponse.of(accessToken, refreshToken, member);
    }
}
