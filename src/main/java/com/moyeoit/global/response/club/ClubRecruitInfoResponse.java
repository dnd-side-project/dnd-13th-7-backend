package com.moyeoit.global.response.club;

import com.moyeoit.domain.app_user.domain.Job;
import com.moyeoit.domain.club.entity.ClubRecruitment;
import com.moyeoit.domain.club.entity.RecruitmentPart;
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
                entity.getRecruitmentParts().stream().map(RecruitmentPart::getJob).map(Job::getName).toList(),
                entity.getQualification(),
                entity.getRecruitmentSchedule(),
                entity.getActivityPeriod(),
                entity.getActivityMethod(),
                entity.getActivityFee(),
                entity.getHomepageUrl(),
                entity.getNoticeUrl()
        );
    }
}
