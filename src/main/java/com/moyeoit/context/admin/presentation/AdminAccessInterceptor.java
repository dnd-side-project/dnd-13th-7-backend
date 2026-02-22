package com.moyeoit.context.admin.presentation;

import com.moyeoit.context.admin.application.AdminAccessTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminAccessInterceptor implements HandlerInterceptor {

    private static final String ADMIN_TOKEN_COOKIE = "ADMIN_TOKEN";
    private final AdminAccessTokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!tokenService.isConfigured()) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Admin code is not configured.");
            return false;
        }

        String token = extractToken(request.getCookies());
        if (!tokenService.isValid(token)) {
            String redirectUrl = request.getContextPath() + "/admin/enter";
            response.sendRedirect(redirectUrl);
            return false;
        }

        return true;
    }

    private String extractToken(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (ADMIN_TOKEN_COOKIE.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
