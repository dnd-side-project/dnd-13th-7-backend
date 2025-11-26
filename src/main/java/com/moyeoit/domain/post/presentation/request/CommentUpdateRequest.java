package com.moyeoit.domain.post.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글 수정 요청")
public class CommentUpdateRequest {
    @Schema(description = "부모 댓글 ID (대댓글일 경우)", example = "1")
    private Long parentId;
    @Schema(description = "수정할 댓글 내용", example = "저도 교촌치킨 좋아해요!")
    private String content;
}
