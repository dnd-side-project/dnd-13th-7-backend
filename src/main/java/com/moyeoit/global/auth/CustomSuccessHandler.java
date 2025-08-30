package com.moyeoit.global.auth;

import com.moyeoit.global.auth.jwt.JwtIssuer;
import com.moyeoit.global.auth.user.CustomOAuth2User;
import com.moyeoit.global.response.user.LoginResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtIssuer jwtIssuer;

    private final String REFRESH_TOKEN_KEY = "REF_TOKEN";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        CustomOAuth2User user = (CustomOAuth2User) token.getPrincipal();

        // 0) 프론트엔드 Redirect-URI 변환
        String redirectURI = request.getParameter("state");
        log.info("Move to '{}' after login.", redirectURI);

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

        String redirectAddress = UriComponentsBuilder.fromUriString(
                        redirectURI + user.getProvider().name().toLowerCase())
                .queryParam("userId", user.getId())
                .queryParam("active", user.isActive())
                .queryParam("accessToken", accessToken)
                .queryParam("expiresIn", 900L)
                .build().toUriString();

        response.sendRedirect(redirectAddress);
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAgeSeconds) {
        // TODO : Secure 설정
        ResponseCookie.ResponseCookieBuilder b = ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofSeconds(maxAgeSeconds))
                .sameSite("Lax");
        response.addHeader("Set-Cookie", b.build().toString());
    }

}
