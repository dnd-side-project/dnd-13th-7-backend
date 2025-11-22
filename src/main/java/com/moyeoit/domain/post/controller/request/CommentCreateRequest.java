package com.moyeoit.domain.post.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글 생성 요청")
public class CommentCreateRequest {
    @Schema(description = "댓글 내용", example = "저도 치킨 좋아해요!")
    private String content;
    @Schema(description = "부모 댓글 ID (대댓글일 경우)", example = "1")
    private Long parentId;
}
