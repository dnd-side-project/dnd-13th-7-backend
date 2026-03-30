package com.moyeoit.global.config.discord;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.discord")
@Getter
@Setter
public class DiscordProperties {
    private String webhookUrl;
}
