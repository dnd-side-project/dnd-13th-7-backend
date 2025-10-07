package com.moyeoit.auth.repository.provider;

import com.moyeoit.auth.repository.OAuthUserInfo;
import com.moyeoit.domain.app_user.domain.AuthProvider;

public interface OAuthProvider {

    String getAuthorizationUrl(String redirectUri, String state);

    AuthProvider getProviderType();

    String getToken(String code, String redirectUri);

    OAuthUserInfo getUserInfo(String token);

}
