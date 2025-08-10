package com.moyeoit.global.config;

import com.moyeoit.global.auth.jwt.JwtIssuer;
import com.moyeoit.global.auth.jwt.JwtValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtIssuer tokenIssuer(@Value("${jwt.secret}") String secret,
                                 @Value("${jwt.access-token-expire-time}") long accessTtl,
                                 @Value("${jwt.refresh-token-expire-time}") long refreshTtl) {
        return new JwtIssuer(secret, accessTtl, refreshTtl);
    }

    @Bean
    public JwtValidator tokenValidator(@Value("${jwt.secret}") String secret) {
        return new JwtValidator(secret);
    }

}