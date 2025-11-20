package com.moyeoit.domain.auth.infra.provider.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moyeoit.domain.auth.infra.OAuthUserInfo;
import com.moyeoit.domain.auth.infra.provider.OAuthProvider;
import com.moyeoit.domain.user.domain.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoProvider implements OAuthProvider {

    private final KakaoProperties properties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String CLIENT_ID_KEY = "client_id";
    private final String CLIENT_SECRET_KEY = "client_secret";
    private final String CODE_KEY = "code";
    private final String GRANT_TYPE_KEY = "grant_type";
    private final String GRANT_TYPE_VALUE = "authorization_code";
    private final String REDIRECT_URI_KEY = "redirect_uri";
    private final String ACCESS_TOKEN_KEY = "access_token";

    @Override
    public String getAuthorizationUrl(String redirectUri, String state) {
        return UriComponentsBuilder
                .fromUriString(properties.getApi().getAuthorizationUri())
                .queryParam("client_id", properties.getAuth().getClientId())
                .queryParam("redirect_uri", redirectUri != null ? redirectUri : properties.getAuth().getRedirectUri())
                .queryParam("response_type", "code")
                .queryParam("scope", String.join(" ", properties.getAuth().getScope()))
                .queryParam("state", "state")
                .build()
                .toUriString();
    }

    @Override
    public String getToken(String code, String redirectUri) {
        try {
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add(CLIENT_ID_KEY, properties.getAuth().getClientId());
            body.add(CLIENT_SECRET_KEY, properties.getAuth().getClientSecret());
            body.add(CODE_KEY, code);
            body.add(REDIRECT_URI_KEY, redirectUri);
            body.add(GRANT_TYPE_KEY, GRANT_TYPE_VALUE);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(properties.getApi().getTokenUri(), request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode root = objectMapper.readTree(response.getBody());
                return root.get(ACCESS_TOKEN_KEY).asText();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public OAuthUserInfo getUserInfo(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // body 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                properties.getApi().getUserInfoUri(),
                HttpMethod.GET,
                request,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                return extractUserInfo(response.getBody());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public AuthProvider getProviderType() {
        return AuthProvider.KAKAO;
    }

    private OAuthUserInfo extractUserInfo(String responseBody) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(responseBody);
        JsonNode kakaoAccount = root.get("kakao_account");

        // 이메일 추출
        String email = (kakaoAccount != null && kakaoAccount.has("email"))
                ? kakaoAccount.get("email").asText()
                : null;

        // 닉네임 추출
        String nickname = null;
        if (kakaoAccount != null && kakaoAccount.has("profile")) {
            JsonNode profile = kakaoAccount.get("profile");
            if (profile.has("nickname")) {
                nickname = profile.get("nickname").asText();
            }
        }

        return new OAuthUserInfo(nickname, email);
    }

}