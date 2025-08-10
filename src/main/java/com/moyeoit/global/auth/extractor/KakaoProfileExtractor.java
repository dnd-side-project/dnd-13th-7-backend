package com.moyeoit.global.auth.extractor;

import com.moyeoit.domain.app_user.domain.AuthProvider;
import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class KakaoProfileExtractor implements ProfileExtractor {

    private final String KAKAO_ACCOUNT_KEY = "kakao_account";
    private final String KAKAO_PROFILE_KEY = "profile";

    private final String KAKAO_NAME_KEY = "nickname";
    private final String KAKAO_EMAIL_KEY = "email";

    @Override
    public OAuth2UserProfile extract(OAuth2User user) {

        Map<String, Object> kakaoAccount = user.getAttribute(KAKAO_ACCOUNT_KEY);
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get(KAKAO_PROFILE_KEY);

        String nickname = (String) profile.get(KAKAO_NAME_KEY);
        String email = (String) kakaoAccount.get(KAKAO_EMAIL_KEY);

        return new OAuth2UserProfile(nickname, email, AuthProvider.KAKAO);
    }

}