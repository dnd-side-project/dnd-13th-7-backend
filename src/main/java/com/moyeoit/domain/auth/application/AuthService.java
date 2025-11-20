package com.moyeoit.domain.auth.application;

import com.moyeoit.domain.auth.infra.provider.OAuthProvider;
import com.moyeoit.domain.user.domain.AuthProvider;
import com.moyeoit.domain.user.service.UserService;
import com.moyeoit.global.auth.jwt.JwtIssuer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final Map<AuthProvider, OAuthProvider> providerMap;
    private final UserService userService;
    private final JwtIssuer jwtIssuer;

    public AuthService(List<OAuthProvider> providers,
                       @Autowired UserService userService,
                       @Autowired JwtIssuer jwtIssuer) {
        this.providerMap = providers.stream()
                .collect(Collectors.toMap(OAuthProvider::getProviderType, p -> p));
        this.userService = userService;
        this.jwtIssuer = jwtIssuer;
    }
}
