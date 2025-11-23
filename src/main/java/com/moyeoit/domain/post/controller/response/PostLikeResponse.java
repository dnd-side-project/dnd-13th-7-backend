package com.moyeoit.domain.post.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 좋아요 응답")
public record PostLikeResponse(
        @Schema(description = "게시글 ID", example = "1")
        Long postId,
        @Schema(description = "좋아요 여부", example = "true")
        boolean liked,
        @Schema(description = "좋아요 수", example = "11")
        int likeCount
) {
}
