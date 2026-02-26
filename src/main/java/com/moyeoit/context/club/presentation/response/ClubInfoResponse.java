package com.moyeoit.context.club.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "동아리 상세 정보 응답")
public record ClubInfoResponse(
        @Schema(description = "동아리 ID", example = "1")
        Long clubId,
        @Schema(description = "동아리 이름", example = "코딩의 민족")
        String clubName,
        @Schema(description = "동아리 대표 이미지 URL", example = "https://moyeoit.s3.ap-northeast-2.amazonaws.com/club_image.jpg")
        String imageUrl,
        @Schema(description = "동아리 상세 소개", example = "실무 중심 프로젝트를 함께 진행하는 IT 동아리입니다.")
        String detailContent,
        @Schema(description = "동아리 홈페이지 URL", example = "https://example.com")
        String homepageUrl
) {
}
