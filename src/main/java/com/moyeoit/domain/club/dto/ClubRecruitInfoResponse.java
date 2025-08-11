package com.moyeoit.domain.club.dto;

import com.moyeoit.domain.club.entity.ClubRecruitment;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public record ClubRecruitInfoResponse(
        List<String> recruitmentPart,
        String qualification,
        String recruitmentSchedule,
        String activityPeriod,
        String activityMethod,
        String activityFee,
        String homepageUrl,
        String noticeUrl) {
    public static ClubRecruitInfoResponse from(ClubRecruitment entity) {
        if (entity == null) {
            return null;
        }

        // 쉼표(,)를 기준으로 분리
        List<String> parts;
        String value = entity.getRecruitment_part();
        if (value != null && !value.isBlank()) {
            parts = Arrays.stream(value.split(","))
                    .map(String::trim) // 각 항목의 앞뒤 공백 제거
                    .toList();
        } else {
            parts = Collections.emptyList(); // 필드가 비어있으면 빈 리스트 반환
        }

        return new ClubRecruitInfoResponse(
                parts,
                entity.getQualification(),
                entity.getRecruitment_schedule(),
                entity.getActivity_period(),
                entity.getActivity_method(),
                entity.getActivity_fee(),
                entity.getHomepage_url(),
                entity.getNotice_url()
        );
    }
}
