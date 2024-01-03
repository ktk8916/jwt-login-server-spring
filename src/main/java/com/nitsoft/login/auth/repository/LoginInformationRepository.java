package com.nitsoft.login.auth.repository;

import com.nitsoft.login.auth.domain.entity.LoginInformation;
import com.nitsoft.login.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoginInformationRepository extends JpaRepository<LoginInformation, Long> {

    @Query("select l " +
            "from LoginInformation l " +
            "inner join l.member " +
            "where l.refreshToken = :refreshToken")
    Optional<LoginInformation> findByRefreshTokenFetchMember(String refreshToken);

    Optional<LoginInformation> findByMember(Member member);
}
