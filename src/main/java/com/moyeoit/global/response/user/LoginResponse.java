package com.moyeoit.global.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Long userId;
    private boolean active;
    private String accessToken;
    private String tokenType;
    private Long expiresIn;

}