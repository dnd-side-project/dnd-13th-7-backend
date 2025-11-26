package com.moyeoit.domain.post.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "인기 게시글 응답")
public record PopularPostResponse(
        @Schema(description = "게시글 ID", example = "1")
        Long postId,
        @Schema(description = "게시글 제목", example = "오늘 저녁 치킨 같이 드실 분?")
        String title,
        @Schema(description = "게시글 발췌", example = "BHC 뿌링클 치킨 기프티콘이 있는데...")
        String excerpt,
        @Schema(description = "카테고리 ID", example = "1")
        Long categoryId,
        @Schema(description = "카테고리 이름", example = "음식")
        String categoryName,
        @Schema(description = "좋아요 수", example = "10")
        Integer likeCount,
        @Schema(description = "댓글 수", example = "5")
        Integer commentCount
) {
}
