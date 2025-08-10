package com.moyeoit.domain.club.dto;

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
}
