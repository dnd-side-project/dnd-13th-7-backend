package com.moyeoit.global.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtIssuer {

    private final SecretKey key;
    private final long accessTTL;
    private final long refreshTTL;

    public JwtIssuer(String secretKey, long accessTTL, long refreshTTL) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.accessTTL = accessTTL;
        this.refreshTTL = refreshTTL;
    }

    public String issue(Long userId, Map<String, Object> claims, long ttl) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(ttl)))
                .signWith(key)
                .compact();
    }

    public JwtCreateResult issueAccess(Long userId, String email, boolean active) {
        String accessTokenValue = issue(userId,
                Map.of("typ", "access", "email", email, "active", active),
                accessTTL);

        return new JwtCreateResult(accessTokenValue, accessTTL, JwtType.ACCESS);
    }

    public String issueRefresh(Long userId) {
        return issue(userId, Map.of("typ", "refresh"), refreshTTL);
    }

}