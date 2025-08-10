package com.moyeoit.global.auth.jwt;

import com.moyeoit.app_user.service.AppUserService;
import com.moyeoit.app_user.service.dto.AppUserDto;
import com.moyeoit.global.auth.user.CustomUserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER = "Bearer ";

    private final JwtValidator jwtValidator;
    private final AppUserService appUserService;

    public JwtFilter(JwtValidator jwtValidator, AppUserService appUserService) {
        this.jwtValidator = jwtValidator;
        this.appUserService = appUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String bearer = request.getHeader(AUTHORIZATION_HEADER);
        String token = (StringUtils.hasText(bearer) && bearer.startsWith(BEARER)) ? bearer.substring(7) : null;

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtValidator.isValid(token)) { // 토큰이 유효하다면
                Long userId = jwtValidator.subject(token).get();
                AppUserDto user = appUserService.getAppUser(userId);

                CustomUserPrincipal principal = new CustomUserPrincipal(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.isActive(),
                        Collections.emptyList()
                );

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}