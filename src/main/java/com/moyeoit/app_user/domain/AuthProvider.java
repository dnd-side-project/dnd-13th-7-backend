package com.moyeoit.app_user.domain;

public enum AuthProvider {

    GOOGLE("구글"),
    KAKAO("카카오");

    private final String name;

    AuthProvider(String name) {
        this.name = name;
    }
    
}