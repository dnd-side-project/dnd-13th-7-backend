package com.moyeoit.context.community.presentation.controller.response;

import com.moyeoit.context.community.domain.Comment;
import java.time.LocalDateTime;

public record PostCommentResponse(
        Long id,
        Long userId,
        Long postId,
        Long parentId,
        String content,
        Integer likeCount,
        Boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostCommentResponse from(Comment comment){
        return new PostCommentResponse(
                comment.getId(),
                comment.getUser().getId(),
                comment.getPost().getId(),
                comment.getParent() != null ? comment.getParent().getId() : null,
                comment.getContent(),
                comment.getLikeCount(),
                comment.getIsDeleted(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
