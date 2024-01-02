package com.nitsoft.login.global.jwt;

import com.nitsoft.login.member.domain.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    public static final long TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 3   ; // 3일

    public String generateToken(Member member) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", member.getId());
        claims.put("username", member.getUsername());
        claims.put("nickname", member.getNickname());

        return buildToken(claims);
    }

    public TokenInfo extractUser(String token){
        Claims claims = extractClaims(token);
        return TokenInfo.builder()
                .id(claims.get("id", Long.class))
                .username(claims.get("username", String.class))
                .nickname(claims.get("nickname", String.class))
                .build();
    }

    private Claims extractClaims(String token) {
        return (Claims) Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parse(token)
                .getBody();
    }

    private String buildToken(Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }
}
