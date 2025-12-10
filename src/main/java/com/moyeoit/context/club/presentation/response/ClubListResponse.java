package com.moyeoit.context.club.presentation.response;

import com.moyeoit.context.club.domain.entity.Club;
import com.moyeoit.context.club.domain.entity.position.ClubPosition;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "동아리 목록 응답")
public record ClubListResponse(
        @Schema(description = "동아리 ID", example = "1")
        Long clubId,
        @Schema(description = "동아리 이름", example = "코딩의 민족")
        String clubName,
        @Schema(description = "동아리 소개", example = "저희는 실무 중심의 프로젝트를 통해 함께 성장하는 IT 동아리입니다.")
        String description,
        @Schema(description = "카테고리 목록", example = "[\"IT\", \"개발\", \"프로젝트\"]")
        List<String> categories,
        @Schema(description = "로고 이미지 URL", example = "https://moyeoit.s3.ap-northeast-2.amazonaws.com/logo.jpg")
        String logoUrl,
        @Schema(description = "모집 여부", example = "true")
        Boolean isRecruiting) {
    public static ClubListResponse from(Club club) {
        return new ClubListResponse(
                club.getId(),
                club.getName(),
                club.getClubProfile().getBio(),
                club.getPositions().stream().map(ClubPosition::getName).toList(),
                club.getClubProfile().getImageUrl(),
                club.getRecruiting()
        );
    }
}
