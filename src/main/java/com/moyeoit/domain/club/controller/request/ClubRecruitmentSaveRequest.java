package com.moyeoit.domain.club.controller.request;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubRecruitment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClubRecruitmentSaveRequest {
    private Long clubId;
    private String qualification;
    private String recruitmentSchedule;
    private String activityPeriod;
    private String activityMethod;
    private String activityFee;
    private String homepageUrl;
    private String noticeUrl;

    public static ClubRecruitment of(ClubRecruitmentSaveRequest request, Club club){
        return ClubRecruitment.builder()
                .club(club)
                .qualification(request.getQualification())
                .recruitmentSchedule(request.getRecruitmentSchedule())
                .activityPeriod(request.getActivityPeriod())
                .activityMethod(request.getActivityMethod())
                .activityFee(request.getActivityFee())
                .homepageUrl(request.getHomepageUrl())
                .noticeUrl(request.getNoticeUrl())
                .build();
    }
}
