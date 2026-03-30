package com.moyeoit.global.notification;

import com.moyeoit.global.config.discord.DiscordProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscordNotificationClient {

    private final RestTemplate restTemplate;
    private final DiscordProperties discordProperties;

    @Async
    public void sendPostNotification(String title, String author, String category, Long postId) {
        String webhookUrl = discordProperties.getWebhookUrl();
        if (webhookUrl == null || webhookUrl.isEmpty()) {
            log.warn("Discord Webhook URL is not configured. Skipping notification.");
            return;
        }

        try {
            String content = String.format("📢 **새로운 커뮤니티 게시글이 등록되었습니다!**\n\n**제목:** %s\n**작성자:** %s\n**카테고리:** %s",
                    title, author, category);

            // Simple payload for Discord Webhook
            DiscordWebhookPayload payload = DiscordWebhookPayload.builder()
                    .content(content)
                    .build();

            restTemplate.postForEntity(webhookUrl, payload, String.class);
            log.info("Discord notification sent for post: {}", postId);
        } catch (Exception e) {
            log.error("Failed to send Discord notification for post: {}", postId, e);
        }
    }

    @Getter
    @Builder
    private static class DiscordWebhookPayload {
        private String content;
    }
}
