package com.nitsoft.login.member.repository;

import com.nitsoft.login.member.domain.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class MemberMemoryRepositoryTest {

    @Autowired
    private MemberMemoryRepository memberRepository;

    @BeforeEach
    void clear(){
        memberRepository.clear();
    }

    @Test
    @DisplayName("회원을 저장한다.")
    void save() {
        // given
        Member member = Member.builder().build();

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(savedMember.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("username으로 회원을 조회한다.")
    void findByUsername() {
        // given
        Member member = Member.builder()
                .username("qwer")
                .password("1234")
                .build();

        memberRepository.save(member);

        // when
        Optional<Member> findMember = memberRepository.findByUsername("qwer");

        // then
        assertThat(findMember).isPresent();
    }

    @Test
    @DisplayName("username과 password로 회원을 조회한다.")
    void findMemberByUsernameAndPassword() {
        // given
        Member member = Member.builder()
                .username("qwer")
                .password("1234")
                .build();

        memberRepository.save(member);

        // when
        Optional<Member> findMember = memberRepository.findByUsernameAndPassword("qwer", "1234");

        // then
        assertThat(findMember).isPresent();
    }


    @Test
    @DisplayName("username과 password로 존재하지 않는 회원을 조회한다.")
    void findExistMemberByUsernameAndPassword() {
        // given
        Member member = Member.builder()
                .username("qwer")
                .password("1234")
                .build();

        memberRepository.save(member);

        // when
        Optional<Member> findMember = memberRepository.findByUsernameAndPassword("qqqqqq", "qqqqqq");

        // then
        assertThat(findMember).isEmpty();
    }

    @Test
    @DisplayName("모든 회원을 조회한다.")
    void findAll() {
        // given
        for (int i = 0; i <5; i++) {
            Member member = Member.builder().build();
            memberRepository.save(member);
        }

        // when
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members).hasSize(5);
    }
}