package com.moyeoit.domain.auth.infra.provider.google;

import com.moyeoit.domain.auth.infra.provider.OAuthProviderProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oauth.google")
public class GoogleProperties extends OAuthProviderProperties {
}