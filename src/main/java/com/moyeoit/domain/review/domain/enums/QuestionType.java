package com.moyeoit.domain.review.domain.enums;

public enum QuestionType {

    SINGLE_SUBJECTIVE("주관식 단일 응답"),
    MULTIPLE_SUBJECTIVE("주관식 다중 응답"),
    SINGLE_CHOICE("객관식 단일 응답"),
    MULTIPLE_CHOICE("객관식 다중 응답"),
    NUMERIC("수치화 응답");

    private final String name;

    QuestionType(String name) {
        this.name = name;
    }

    public boolean isChoice() {
        if (this.equals(MULTIPLE_CHOICE) || this.equals(SINGLE_CHOICE)) return true;
        return false;
    }

    public boolean isSubjective() {
        if (this.equals(MULTIPLE_SUBJECTIVE) || this.equals(SINGLE_SUBJECTIVE)) return true;
        return false;
    }
    
}
