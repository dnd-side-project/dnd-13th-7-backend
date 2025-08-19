package com.moyeoit.domain.review.domain;

public enum ReviewType {

    BASIC("일반"),
    PREMIUM("프리미엄");

    private final String name;

    ReviewType(String name) {
        this.name = name;
    }

}