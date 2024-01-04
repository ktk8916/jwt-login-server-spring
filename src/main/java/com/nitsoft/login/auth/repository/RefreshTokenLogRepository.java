package com.nitsoft.login.auth.repository;

import com.nitsoft.login.auth.domain.entity.RefreshTokenLog;
import com.nitsoft.login.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenLogRepository extends JpaRepository<RefreshTokenLog, Long> {

    @Query("select l " +
            "from RefreshTokenLog l " +
            "inner join l.member " +
            "where l.refreshToken = :refreshToken")
    Optional<RefreshTokenLog> findByRefreshTokenFetchMember(String refreshToken);

    Optional<RefreshTokenLog> findByMember(Member member);
}
