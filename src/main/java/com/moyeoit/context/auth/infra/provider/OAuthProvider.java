package com.moyeoit.context.auth.infra.provider;

import com.moyeoit.context.auth.infra.OAuthUserInfo;
import com.moyeoit.context.user.domain.AuthProvider;

public interface OAuthProvider {

    String getAuthorizationUrl(String redirectUri, String state);

    AuthProvider getProviderType();

    String getToken(String code, String redirectUri);

    OAuthUserInfo getUserInfo(String token);

}
