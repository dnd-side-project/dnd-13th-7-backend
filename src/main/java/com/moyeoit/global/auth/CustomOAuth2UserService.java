package com.moyeoit.global.auth;

import com.moyeoit.domain.app_user.service.AppUserService;
import com.moyeoit.domain.app_user.service.dto.AppUserDto;
import com.moyeoit.global.auth.extractor.GoogleProfileExtractor;
import com.moyeoit.global.auth.extractor.KakaoProfileExtractor;
import com.moyeoit.global.auth.extractor.ProfileExtractor;
import com.moyeoit.global.auth.user.CustomOAuth2User;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.BaseErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final String GOOGLE_REGISTRATION = "google";
    private final String KAKAO_REGISTRATION = "kakao";

    private final AppUserService appUserService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest req) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User user = delegate.loadUser(req);

        ProfileExtractor profileExtractor = getProfileExtractor(req);

        AppUserDto userDto = appUserService.findOrCreateAppUserFromOAuth2(profileExtractor.extract(user));

        return new CustomOAuth2User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.isActive(),
                null
        );
    }

    private ProfileExtractor getProfileExtractor(OAuth2UserRequest req) {
        if (req.getClientRegistration().getRegistrationId().equals(GOOGLE_REGISTRATION)) {
            return new GoogleProfileExtractor();
        }

        if (req.getClientRegistration().getRegistrationId().equals(KAKAO_REGISTRATION)) {
            return new KakaoProfileExtractor();
        }

        throw new AppException(BaseErrorCode.UNAUTHORIZED);
    }

}