package com.moyeoit.global.config;

import com.moyeoit.domain.app_user.service.AppUserService;
import com.moyeoit.global.auth.CustomOAuth2AuthorizationRequestResolver;
import com.moyeoit.global.auth.CustomOAuth2UserService;
import com.moyeoit.global.auth.CustomSuccessHandler;
import com.moyeoit.global.auth.jwt.JwtFilter;
import com.moyeoit.global.auth.jwt.JwtValidator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final ClientRegistrationRepository clientRegistrationRepository;

    private final String AUTHORIZATION_ENDPOINT_URI = "/api/oauth2/authorize";
    private final String REDIRECTION_ENDPOINT_URI = "/api/oauth2/login/*";

    @Bean //cors 설정 빈
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        config.setExposedHeaders(List.of("*", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtValidator jwtValidator,
                                           AppUserService appUserService) throws Exception {
        JwtFilter jwtFilter = new JwtFilter(jwtValidator, appUserService);

        //cors 설정
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        //csrf disable
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.securityContext(sc -> sc.securityContextRepository(new NullSecurityContextRepository()));

        //form 로그인 방식 disable
        http.formLogin(AbstractHttpConfigurer::disable);

        //http basic 인증방식 disable
        http.httpBasic(AbstractHttpConfigurer::disable);

        //경로별 인가작업 -> 모두 허용
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        http.oauth2Login(oauth -> oauth
                .authorizationEndpoint(ep -> {
                    ep.baseUri(AUTHORIZATION_ENDPOINT_URI);
                    ep.authorizationRequestResolver(
                            new CustomOAuth2AuthorizationRequestResolver(clientRegistrationRepository));
                })
                .redirectionEndpoint(redir -> redir.baseUri(REDIRECTION_ENDPOINT_URI))
                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                .successHandler(customSuccessHandler)
        );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        //h2 콘솔 사용을 위한 같은 Origin iframe 허용
        http.headers(headersConfigurer ->
                headersConfigurer
                        .frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
        );

        return http.build();
    }
}
