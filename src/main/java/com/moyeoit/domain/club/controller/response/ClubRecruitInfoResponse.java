package com.moyeoit.domain.club.controller.response;

import com.moyeoit.domain.club.entity.ClubRecruitment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "동아리 모집 정보 응답")
public record ClubRecruitInfoResponse(
        @Schema(description = "모집 분야", example = "[\"프론트엔드\", \"백엔드\", \"디자이너\"]")
        List<String> recruitmentPart,
        @Schema(description = "지원 자격", example = "코딩에 대한 열정이 있는 누구나")
        String qualification,
        @Schema(description = "모집 일정", example = "3월 1일 ~ 3월 15일")
        String recruitmentSchedule,
        @Schema(description = "활동 기간", example = "1년")
        String activityPeriod,
        @Schema(description = "활동 방식", example = "온/오프라인 병행")
        String activityMethod,
        @Schema(description = "활동비", example = "학기당 5만원")
        String activityFee,
        @Schema(description = "홈페이지 URL", example = "https://coding-nation.com")
        String homepageUrl,
        @Schema(description = "공지사항 URL", example = "https://coding-nation.com/notice")
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
