package com.moyeoit.auth.repository.provider.kakao;

import com.moyeoit.auth.repository.provider.OAuthProviderProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oauth.kakao")
public class KakaoProperties extends OAuthProviderProperties {
}
