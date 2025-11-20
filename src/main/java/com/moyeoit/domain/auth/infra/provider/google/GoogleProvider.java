package com.moyeoit.domain.auth.infra.provider.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moyeoit.domain.auth.infra.OAuthUserInfo;
import com.moyeoit.domain.auth.infra.provider.OAuthProvider;
import com.moyeoit.domain.user.domain.AuthProvider;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleProvider implements OAuthProvider {

    private final GoogleProperties properties;
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
            String decodeCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add(CODE_KEY, decodeCode);
            body.add(CLIENT_ID_KEY, properties.getAuth().getClientId());
            body.add(CLIENT_SECRET_KEY, properties.getAuth().getClientSecret());
            body.add(REDIRECT_URI_KEY, redirectUri);
            body.add(GRANT_TYPE_KEY, GRANT_TYPE_VALUE);

            // 요청 Header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 요청 Entity 생성
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            // POST 요청
            ResponseEntity<String> response = restTemplate.postForEntity(properties.getApi().getTokenUri(), request, String.class);
            if (!response.getStatusCode().is2xxSuccessful())
                throw new AppException(AuthErrorCode.FAILED_GET_TOKEN);

            JsonNode root = objectMapper.readTree(response.getBody());
            return root.get(ACCESS_TOKEN_KEY).asText();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(AuthErrorCode.FAILED_GET_TOKEN);
        }

    }

    @Override
    public OAuthUserInfo getUserInfo(String token) {
        try {
            // Header 생성
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            // 요청 Entity 설정
            HttpEntity<Void> request = new HttpEntity<>(headers);

            // GET 요청
            ResponseEntity<String> response = restTemplate.exchange(
                    properties.getApi().getUserInfoUri(),
                    HttpMethod.GET,
                    request,
                    String.class);

            if (!response.getStatusCode().is2xxSuccessful())
                throw new AppException(AuthErrorCode.FAILED_GET_USER_INFO);
            return extractUserInfo(response.getBody());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(AuthErrorCode.FAILED_GET_USER_INFO);
        }
    }

    @Override
    public AuthProvider getProviderType() {
        return AuthProvider.GOOGLE;
    }

    private OAuthUserInfo extractUserInfo(String responseBody) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(responseBody);

        String name = root.has("name") ? root.get("name").asText() : null;

        String email = root.has("email") ? root.get("email").asText() : null;

        return new OAuthUserInfo(name, email);
    }
}
