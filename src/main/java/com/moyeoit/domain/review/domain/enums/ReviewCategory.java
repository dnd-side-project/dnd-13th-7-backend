package com.moyeoit.domain.review.domain.enums;

public enum ReviewCategory {

    DOCUMENT("서류"),
    INTERVIEW("인터뷰"),
    ACTIVITY("활동"),
    BLOG("블로그");

    private final String label;

    ReviewCategory(String label) {
        this.label = label;
    }

}
