package com.moyeoit.domain.review.domain;

import java.util.Arrays;

public enum ReviewType {

    BASIC("일반"),
    PREMIUM("프리미엄");

    private final String name;

    ReviewType(String name) {
        this.name = name;
    }
    public static ReviewType fromString(String name) {
        return Arrays.stream(ReviewType.values())
                .filter(type -> type.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 name입니다: " + name));
    }

}