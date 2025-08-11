package com.moyeoit.domain.club.dto;

import com.moyeoit.domain.club.entity.ClubRecruitment;
import com.moyeoit.domain.club.entity.RecruitmentPart;
import java.util.List;

public record ClubRecruitInfoResponse(
        List<RecruitmentPart> recruitmentPart,
        String qualification,
        String recruitmentSchedule,
        String activityPeriod,
        String activityMethod,
        String activityFee,
        String homepageUrl,
        String noticeUrl) {
    public static ClubRecruitInfoResponse from(ClubRecruitment entity) {

        return new ClubRecruitInfoResponse(
                entity.getRecruitmentParts(),
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
