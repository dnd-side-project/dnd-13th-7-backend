package com.moyeoit.domain.review.domain.enums;

public enum ReviewSort {
    POPULAR("인기순"),
    LATEST("최신순");

    private final String label;

    ReviewSort(String label) {
        this.label = label;
    }
    
}
