package com.moyeoit.domain.auth.infra.provider;

import com.moyeoit.domain.auth.infra.OAuthUserInfo;
import com.moyeoit.domain.user.domain.AuthProvider;

public interface OAuthProvider {

    String getAuthorizationUrl(String redirectUri, String state);

    AuthProvider getProviderType();

    String getToken(String code, String redirectUri);

    OAuthUserInfo getUserInfo(String token);

}
