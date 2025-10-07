package com.moyeoit.auth.service;

import com.moyeoit.auth.controller.request.AuthRequest;
import com.moyeoit.auth.controller.response.AuthResponse;
import com.moyeoit.auth.repository.OAuthUserInfo;
import com.moyeoit.auth.repository.provider.OAuthProvider;
import com.moyeoit.domain.app_user.domain.AuthProvider;
import com.moyeoit.domain.app_user.service.AppUserService;
import com.moyeoit.domain.app_user.service.dto.AppUserDto;
import com.moyeoit.global.auth.jwt.JwtCreateResult;
import com.moyeoit.global.auth.jwt.JwtIssuer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final Map<AuthProvider, OAuthProvider> providerMap;
    private final AppUserService appUserService;
    private final JwtIssuer jwtIssuer;


    public AuthService(List<OAuthProvider> providers,
                       @Autowired AppUserService appUserService,
                       @Autowired JwtIssuer jwtIssuer) {
        this.providerMap = providers.stream()
                .collect(Collectors.toMap(OAuthProvider::getProviderType, p -> p));
        this.appUserService = appUserService;
        this.jwtIssuer = jwtIssuer;


    }

    public String getAuthorizationUrl(String redirectUri, String state, AuthProvider providerType) {
        OAuthProvider provider = providerMap.get(providerType);
        return provider.getAuthorizationUrl(redirectUri, state);
    }

    public AuthResponse login(AuthRequest request) {
        OAuthProvider provider = providerMap.get(request.getProviderType());
        if (provider == null) throw new IllegalArgumentException("Invalid provider type");

        String oauthAccessToken = provider.getToken(request.getCode(), request.getRedirectUri());
        OAuthUserInfo userInfo = provider.getUserInfo(oauthAccessToken);

        // 이메일, Provider 기반으로 회원가입 여부 확인 및 생성
        AppUserDto userDto = appUserService.findOrCreateAppUserFromOAuth("", userInfo.getUserEmail(), request.getProviderType());

        // JWT 생성
        JwtCreateResult tokenResult = jwtIssuer.issueAccess(userDto.getId(), userDto.getEmail(), userDto.isActive());

        return new AuthResponse(
                tokenResult.getAccessToken(),
                tokenResult.getAccessExpiresIn(),
                request.getState()
        );
    }

}
