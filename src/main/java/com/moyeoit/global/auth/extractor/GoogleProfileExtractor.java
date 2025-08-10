package com.moyeoit.global.auth.extractor;

import com.moyeoit.app_user.domain.AuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleProfileExtractor implements ProfileExtractor {

    private final String GOOGLE_NAME_KEY = "name";
    private final String GOOGLE_EMAIL_KEY = "email";

    @Override
    public OAuth2UserProfile extract(OAuth2User user) {

        String name = user.getAttribute(GOOGLE_NAME_KEY);
        String email = user.getAttribute(GOOGLE_EMAIL_KEY);

        return new OAuth2UserProfile(name, email, AuthProvider.GOOGLE);
    }
}