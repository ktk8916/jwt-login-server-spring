package com.nitsoft.login.member.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

    private Long id;
    private String username;
    private String password;
    private String nickname;

    public void setSequenceId(Long id){
        this.id = id;
    }

    public boolean checkLoginInfo(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
    }

    @Builder
    public Member(Long id, String username, String password, String nickname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
