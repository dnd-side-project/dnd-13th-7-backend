package com.moyeoit.global.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtCreateResult {

    private String accessToken;
    private Long accessExpiresIn;
    private JwtType type;

}
