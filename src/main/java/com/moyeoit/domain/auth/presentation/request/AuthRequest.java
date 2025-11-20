package com.moyeoit.domain.auth.presentation.request;

import com.moyeoit.domain.user.domain.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String code;
    private String state;
    private String redirectUri;
    private AuthProvider providerType;

}