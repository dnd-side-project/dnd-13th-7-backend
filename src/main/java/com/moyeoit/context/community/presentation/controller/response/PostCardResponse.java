package com.moyeoit.context.community.presentation.controller.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "게시글 카드 응답")
public record PostCardResponse (
        @Schema(description = "게시글 ID", example = "1")
        Long postId,
        @Schema(description = "게시글 제목", example = "오늘 저녁 치킨 같이 드실 분?")
        String title,
        @Schema(description = "게시글 발췌", example = "BHC 뿌링클 치킨 기프티콘이 있는데...")
        String excerpt,
        @Schema(description = "썸네일 이미지 URL", example = "https://moyeoit.s3.ap-northeast-2.amazonaws.com/thumbnail.jpg")
        String thumbnailUrl,
        @Schema(description = "카테고리 ID", example = "1")
        Long categoryId,
        @Schema(description = "카테고리 이름", example = "음식")
        String categoryName,
        @Schema(description = "작성자 닉네임", example = "치킨마니아")
        String authorNickname,
        @Schema(description = "조회수", example = "100")
        Integer viewCount,
        @Schema(description = "좋아요 수", example = "10")
        Integer likeCount,
        @Schema(description = "댓글 수", example = "5")
        Integer commentCount,
        @Schema(description = "작성일")
        LocalDateTime createdAt
){
    @QueryProjection
    public PostCardResponse {
    }
}
