package com.moyeoit.domain.auth.infra.provider;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OAuthProviderProperties {

    private Auth auth;
    private Api api;

    @Getter
    @Setter
    public static class Auth {
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private List<String> scope;
    }

    @Getter
    @Setter
    public static class Api {
        private String authorizationUri;
        private String tokenUri;
        private String userInfoUri;
    }
}