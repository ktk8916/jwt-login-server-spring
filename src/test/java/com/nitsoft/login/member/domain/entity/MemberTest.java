package com.nitsoft.login.member.domain.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @ParameterizedTest
    @CsvSource(value = {"qwer, 1234, true", "qqqqqqqqq, wwwwwwwww, false"})
    @DisplayName("username과 password가 맞는지 확인한다.")
    void checkLoginInfo(String username, String password, boolean expected) {
        // given
        Member member = Member.builder()
                .username("qwer")
                .password("1234")
                .build();

        // when
        boolean loginInfo = member.checkLoginInfo(username, password);

        // then
        assertThat(loginInfo).isEqualTo(expected);
    }
}