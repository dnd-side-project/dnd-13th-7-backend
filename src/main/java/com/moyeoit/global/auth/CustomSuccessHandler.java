package com.moyeoit.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moyeoit.global.response.user.LoginResponse;
import com.moyeoit.global.auth.jwt.JwtIssuer;
import com.moyeoit.global.auth.user.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtIssuer jwtIssuer;
    private final ObjectMapper objectMapper;

    private final String REFRESH_TOKEN_KEY = "REF_TOKEN";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        CustomOAuth2User user = (CustomOAuth2User) token.getPrincipal();

        // 1) AccessToken 발급
        String accessToken = jwtIssuer.issueAccess(user.getId(), user.getEmail(), user.isActive());

        LoginResponse loginResponse = new LoginResponse(
                user.getId(),
                user.isActive(),
                accessToken,
                "Bearer",
                900L
        );

        // 2) RefreshToken 발급
        String refreshToken = jwtIssuer.issueRefresh(user.getId());
        addCookie(response, REFRESH_TOKEN_KEY, refreshToken, 604800);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), loginResponse);
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAgeSeconds) {
        // TODO : Secure 설정
        ResponseCookie.ResponseCookieBuilder b = ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofSeconds(maxAgeSeconds))
                .sameSite("Lax");
        response.addHeader("Set-Cookie", b.build().toString());
    }

}
