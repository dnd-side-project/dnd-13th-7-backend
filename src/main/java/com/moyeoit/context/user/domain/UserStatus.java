package com.moyeoit.context.user.domain;

public enum UserStatus {

    OFFICE_WORKER("직장인"),
    STUDENT("학생"),
    JOB_SEEKER("취준생");

    private final String label;

    UserStatus(String label) {
        this.label = label;
    }

}