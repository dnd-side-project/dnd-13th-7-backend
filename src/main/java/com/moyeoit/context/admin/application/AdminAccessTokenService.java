package com.moyeoit.context.admin.application;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AdminAccessTokenService {

    private static final Duration TTL = Duration.ofHours(8);
    private final String adminCode;

    public AdminAccessTokenService(@Value("${admin.code:}") String adminCode) {
        this.adminCode = adminCode;
    }

    public boolean isConfigured() {
        return StringUtils.hasText(adminCode);
    }

    public boolean matchesCode(String code) {
        return StringUtils.hasText(code) && code.equals(adminCode);
    }

    public long ttlSeconds() {
        return TTL.toSeconds();
    }

    public String createToken() {
        if (!isConfigured()) {
            throw new IllegalStateException("Admin code is not configured.");
        }
        String payload = "admin:" + Instant.now().getEpochSecond();
        String signature = sign(payload);
        return base64Url(payload) + "." + signature;
    }

    public boolean isValid(String token) {
        if (!isConfigured() || !StringUtils.hasText(token)) {
            return false;
        }
        String[] parts = token.split("\\.");
        if (parts.length != 2) {
            return false;
        }
        String payload;
        try {
            payload = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            return false;
        }

        String expectedSignature = sign(payload);
        if (!constantTimeEquals(expectedSignature, parts[1])) {
            return false;
        }

        String[] payloadParts = payload.split(":");
        if (payloadParts.length != 2) {
            return false;
        }
        long issuedAt;
        try {
            if (!"admin".equals(payloadParts[0])) {
                return false;
            }
            issuedAt = Long.parseLong(payloadParts[1]);
        } catch (NumberFormatException e) {
            return false;
        }
        Instant expiresAt = Instant.ofEpochSecond(issuedAt).plus(TTL);
        return Instant.now().isBefore(expiresAt);
    }

    private String sign(String payload) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(adminCode.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signature = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(signature);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to sign admin token", e);
        }
    }

    private String base64Url(String raw) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }

    private boolean constantTimeEquals(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}
