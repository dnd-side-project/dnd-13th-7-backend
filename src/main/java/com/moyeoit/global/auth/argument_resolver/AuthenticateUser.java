package com.moyeoit.global.auth.argument_resolver;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticateUser implements AccessUser {

    private final Long id;
    private final String name;
    private final String email;
    private final boolean active;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public boolean isActive() {
        return active;
    }
    
}
