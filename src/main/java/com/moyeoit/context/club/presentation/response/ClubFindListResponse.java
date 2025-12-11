package com.moyeoit.context.club.presentation.response;

import com.moyeoit.context.club.domain.entity.Club;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "동아리 검색 결과 응답")
public record ClubFindListResponse(
        @Schema(description = "동아리 ID", example = "1")
        Long clubId,
        @Schema(description = "동아리 이름", example = "코딩의 민족")
        String name,
        @Schema(description = "이미지 URL", example = "https://moyeoit.s3.ap-northeast-2.amazonaws.com/logo.jpg")
        String imgUrl
) {
    public static ClubFindListResponse from(Club club) {
        return new ClubFindListResponse(
                club.getId(),
                club.getName(),
                club.getClubProfile().imageUrl()
        );
    }
}
