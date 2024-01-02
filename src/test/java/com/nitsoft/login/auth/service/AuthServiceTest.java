package com.nitsoft.login.auth.service;

import com.nitsoft.login.auth.domain.request.LoginRequest;
import com.nitsoft.login.auth.domain.request.SignupRequest;
import com.nitsoft.login.auth.domain.response.LoginResponse;
import com.nitsoft.login.global.exception.ApiException;
import com.nitsoft.login.member.domain.entity.Member;
import com.nitsoft.login.member.repository.MemberMemoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.nitsoft.login.global.exception.ExceptionType.MEMBER_NOT_FOUND;
import static com.nitsoft.login.global.exception.ExceptionType.USERNAME_ALREADY_REGISTERED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @MockBean
    private MemberMemoryRepository memberRepository;

    @Test
    @DisplayName("신규 회원가입 후 토큰을 발급받는다.")
    void signup() {
        // given
        Member member = getTestMember();

        when(memberRepository.findByUsername("username"))
                .thenReturn(Optional.empty());

        when(memberRepository.save(any()))
                .thenReturn(member);

        SignupRequest request = SignupRequest.builder()
                .username("username")
                .password("password")
                .nickname("nickname")
                .build();

        // when
        LoginResponse response = authService.signup(request);

        // then
        assertThat(response.accessToken()).isNotBlank();
    }

    @Test
    @DisplayName("이미 등록된 username으로는 회원가입 할 수 없다.")
    void signupDuplicateUsername() {
        // given
        Member member = getTestMember();

        when(memberRepository.findByUsername("username"))
                .thenReturn(Optional.of(member));

        when(memberRepository.save(any()))
                .thenReturn(member);

        SignupRequest request = SignupRequest.builder()
                .username("username")
                .password("password")
                .nickname("nickname")
                .build();

        // when, then
        assertThatThrownBy(()->authService.signup(request))
                .isInstanceOf(ApiException.class)
                .hasMessage(USERNAME_ALREADY_REGISTERED.getMessage());
    }

    @Test
    @DisplayName("로그인 후 토큰을 발급받는다.")
    void login() {
        // given
        Member member = getTestMember();

        when(memberRepository.findByUsernameAndPassword("username", "password"))
                .thenReturn(Optional.of(member));

        LoginRequest request = LoginRequest.builder()
                .username("username")
                .password("password")
                .build();

        // when
        LoginResponse response = authService.login(request);

        // then
        assertThat(response.accessToken()).isNotBlank();
    }

    @Test
    @DisplayName("로그인 정보가 유효하지 않으면 예외가 발생한다.")
    void loginWithInvalidRequest() {
        // given
        when(memberRepository.findByUsernameAndPassword("username", "password"))
                .thenReturn(Optional.empty());

        // when
        LoginRequest request = LoginRequest.builder()
                .username("누가봐도틀림")
                .password("이거도무조건틀림")
                .build();

        // then
        assertThatThrownBy(()->authService.login(request))
                .isInstanceOf(ApiException.class)
                .hasMessage(MEMBER_NOT_FOUND.getMessage());
    }

    private Member getTestMember() {
        Member member = Member.builder()
                .username("username")
                .password("password")
                .nickname("nickname")
                .build();
        member.setSequenceId(1L);
        return member;
    }

}