package com.moyeoit.context.community.domain;

public enum CommunityCategoryType {
    POPULAR("인기"),
    QUESTION("질문"),
    FREE("자유"),
    IT_CLUB("IT동아리"),
    CAMPUS_LIFE("대학생활"),
    WORK_LIFE("직장생활"),
    CAREER_CHANGE("이직/커리어"),
    JOB_PREP("취업준비");

    private final String displayName;

    CommunityCategoryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isPopular() {
        return this == POPULAR;
    }

    public static CommunityCategoryType from(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        for (CommunityCategoryType type : values()) {
            if (type.name().equalsIgnoreCase(trimmed) || type.displayName.equalsIgnoreCase(trimmed)) {
                return type;
            }
        }
        return null;
    }
}
