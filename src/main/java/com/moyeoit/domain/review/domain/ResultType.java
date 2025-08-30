package com.moyeoit.domain.review.domain;

import java.util.Arrays;

public enum ResultType {

    PASS("합격"),
    FAIL("불합격"),
    READY("결과 대기중");

    public final String name;

    ResultType(String name) {
        this.name = name;
    }
    public static ResultType fromName(String name){
        return Arrays.stream(values())
                .filter(type -> type.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 ResultType이 없습니다: " + name));
    }

}

