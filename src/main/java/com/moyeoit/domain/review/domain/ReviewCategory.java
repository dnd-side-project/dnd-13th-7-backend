package com.moyeoit.domain.review.domain;

public enum ReviewCategory {

    DOCUMENT("서류"),
    INTERVIEW("인터뷰"),
    ACTIVITY("활동");

    public final String name;

    ReviewCategory(String name) {
        this.name = name;
    }
    
}