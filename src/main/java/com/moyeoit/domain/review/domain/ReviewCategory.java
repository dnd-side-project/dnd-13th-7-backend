package com.moyeoit.domain.review.domain;

import java.util.Arrays;

public enum ReviewCategory {

    DOCUMENT("서류"),
    INTERVIEW("인터뷰"),
    ACTIVITY("활동");

    public final String name;

    ReviewCategory(String name) {
        this.name = name;
    }

    public static ReviewCategory fromName(String name){
        return Arrays.stream(values())
                .filter(type -> type.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 ResultType이 없습니다: " + name));
    }
    
}