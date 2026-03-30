package com.moyeoit.global.notification;

import com.moyeoit.global.config.discord.DiscordProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscordNotificationClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DiscordProperties discordProperties;

    @InjectMocks
    private DiscordNotificationClient discordNotificationClient;

    private final String WEBHOOK_URL = "https://discord.com/api/webhooks/test";

    @Test
    @DisplayName("디스코드 알림이 정상적으로 전송된다")
    void sendNotification_Success() {
        // given
        when(discordProperties.getWebhookUrl()).thenReturn(WEBHOOK_URL);

        // when
        discordNotificationClient.sendPostNotification("제목", "작성자", "카테고리", 1L);

        // then
        verify(restTemplate, times(1)).postForEntity(eq(WEBHOOK_URL), any(), eq(String.class));
    }

    @Test
    @DisplayName("웹훅 URL이 없으면 전송하지 않는다")
    void sendNotification_NoUrl() {
        // given
        when(discordProperties.getWebhookUrl()).thenReturn(null);

        // when
        discordNotificationClient.sendPostNotification("제목", "작성자", "카테고리", 1L);

        // then
        verify(restTemplate, never()).postForEntity(any(), any(), any());
    }
}
