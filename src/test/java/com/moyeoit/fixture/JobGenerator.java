package com.moyeoit.fixture;

import com.moyeoit.domain.user.domain.Job;

public class JobGenerator {

    /**
     * 한글/영어 이름 기반의 직무를 생성합니다.
     */
    public static Job createJob(String name, String engName) {
        return Job.builder()
                .name(name)
                .engName(engName)
                .build();
    }

}
