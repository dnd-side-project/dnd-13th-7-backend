package com.moyeoit.global.auth.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

    private final Long id;
    private final String name;
    private final String email;
    private final boolean active;

    private final Map<String, Object> attributes;

    public CustomOAuth2User(Long id,
                            String name,
                            String email,
                            boolean active,
                            Map<String, Object> attributes) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = active;
        this.attributes = attributes;
    }

    /**
     * 고유 식별자 반환
     */
    @Override
    public String getName() {
        return String.valueOf(id);
    }

    public Long getId() {
        return id;
    }

    public String getNameField() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }


}