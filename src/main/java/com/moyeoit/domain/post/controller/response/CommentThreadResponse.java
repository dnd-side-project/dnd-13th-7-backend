package com.moyeoit.domain.post.controller.response;

import java.util.List;

public record CommentThreadResponse(
        PostCommentResponse parent,            // 상위 댓글
        List<PostCommentResponse> children
) {
}
