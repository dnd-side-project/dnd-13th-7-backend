package com.moyeoit.domain.club.controller.response;

import com.moyeoit.domain.club.entity.ClubRecruitment;
import com.moyeoit.domain.club.entity.ClubRecruitmentPart;
import com.moyeoit.domain.user.domain.Job;

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

        return new ClubRecruitInfoResponse(
                entity.getClubRecruitmentParts().stream().map(rec -> rec.getId().toString()).toList(),
                entity.getQualification(),
                entity.getRecruitmentSchedule(),
                entity.getActivityPeriod(),
                entity.getActivityMethod(),
                entity.getActivityFee().toString(),
                entity.getHomepageUrl(),
                entity.getNoticeUrl()
        );
    }
}
