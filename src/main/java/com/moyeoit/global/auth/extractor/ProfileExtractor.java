package com.moyeoit.global.auth.extractor;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface ProfileExtractor {

    OAuth2UserProfile extract(OAuth2User user);

}