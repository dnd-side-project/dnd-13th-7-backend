package com.moyeoit.global.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtValidator {

    private final SecretKey key;

    public JwtValidator(String secretKey) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Long> subject(String token) {
        try {
            String sub = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

            if (sub == null) {
                return Optional.empty();
            }
            return Optional.of(Long.parseLong(sub));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

}