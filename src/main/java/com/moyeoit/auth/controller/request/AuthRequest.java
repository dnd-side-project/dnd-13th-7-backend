package com.moyeoit.auth.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moyeoit.domain.app_user.domain.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @JsonProperty("code")
    private String code;

    @JsonProperty("state")
    private String state;

    @JsonProperty("redirect_uri")
    private String redirectUri;

    @JsonProperty("type")
    private AuthProvider providerType;

}
