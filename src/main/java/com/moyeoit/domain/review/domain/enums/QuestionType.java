package com.moyeoit.domain.review.domain.enums;

public enum QuestionType {

    SUBJECTIVE("주관식"),
    SINGLE_CHOICE("객관식 단일 응답"),
    MULTIPLE_CHOICE("객관식 다중 응답");

    private final String name;

    QuestionType(String name) {
        this.name = name;
    }

}
