package com.moyeoit.domain.review.domain.enums;

public enum ReviewResult {

    PASS("합격"),
    FAILURE("불합격"),
    NOT_PARTICIPATE_AFTER_PASS("합격 후 참여하지 않음"),
    WAITING("결과 대기중"),
    ACTIVITY("활동 중"),
    END_ACTIVITY("활동 종료");

    private final String label;

    ReviewResult(String label) {
        this.label = label;
    }

    public boolean isValidType(ReviewCategory category) {
        if (ReviewCategory.ACTIVITY.equals(category)) {
            return this.equals(ACTIVITY) || this.equals(END_ACTIVITY);
        }
        return !this.equals(ACTIVITY) && !this.equals(END_ACTIVITY);
    }

}