package com.moyeoit.context.auth.infra.provider.kakao;

import com.moyeoit.context.auth.infra.provider.OAuthProviderProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oauth.kakao")
public class KakaoProperties extends OAuthProviderProperties {
}