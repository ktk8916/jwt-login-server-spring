package com.nitsoft.login.auth.domain.entity;

import com.nitsoft.login.global.BaseEntity;
import com.nitsoft.login.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginInformation extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Member member;
    private String refreshToken;

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    @Builder
    public LoginInformation(Member member, String refreshToken) {
        this.member = member;
        this.refreshToken = refreshToken;
    }
}
