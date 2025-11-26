package com.moyeoit.domain.post.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "댓글 스레드 응답")
public record CommentThreadResponse(
        @Schema(description = "상위 댓글")
        PostCommentResponse parent,
        @Schema(description = "대댓글 목록")
        List<PostCommentResponse> children
) {
}
