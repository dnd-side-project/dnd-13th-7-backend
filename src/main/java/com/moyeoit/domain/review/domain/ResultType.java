package com.moyeoit.domain.review.domain;

public enum ResultType {

    PASS("합격"),
    FAIL("불합격"),
    READY("결과 대기중");

    public final String name;

    ResultType(String name) {
        this.name = name;
    }

}
