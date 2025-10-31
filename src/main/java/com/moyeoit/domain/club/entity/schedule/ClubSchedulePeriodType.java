package com.moyeoit.domain.club.entity.schedule;

public enum ClubSchedulePeriodType {

    DAY("일"),
    WEEK("주"),
    MONTH("개월"),
    YEAR("년");

    private final String label;

    ClubSchedulePeriodType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}