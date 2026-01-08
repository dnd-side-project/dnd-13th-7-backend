package com.moyeoit.context.club.application.dto;

import com.moyeoit.context.club.domain.entity.activity.ClubActivity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "동아리 활동 정보")
public record ClubActivityDto(
        @Schema(description = "해시태그", example = "#프로젝트")
        String hashtag,
        @Schema(description = "활동 이름", example = "토이 프로젝트")
        String activityName,
        @Schema(description = "활동 설명", example = "실제 서비스를 개발하고 배포하는 경험을 합니다.")
        String activityDescribe,
        @Schema(description = "활동 이미지 URL", example = "https://moyeoit.s3.ap-northeast-2.amazonaws.com/activity_image.jpg")
        String imageUrl,
        @Schema(description = "활동 순서", example = "1")
        Integer activityOrder) {

    public static ClubActivityDto from(ClubActivity entity) {
        return ClubActivityDto.builder()
                .hashtag(entity.getHashtag())
                .activityName(entity.getName())
                .activityDescribe(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .activityOrder(entity.getSequence())
                .build();
    }
}
