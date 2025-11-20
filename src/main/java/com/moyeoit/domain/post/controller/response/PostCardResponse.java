package com.moyeoit.domain.post.controller.response;

import java.time.LocalDateTime;

public record PostCardResponse (
        Long postId,
        String title,
        String excerpt,
        String thumbnailUrl,
        Long categoryId,
        String categoryName,
        String authorNickname,
        Integer viewCount,
        Integer likeCount,
        Integer commentCount,
        LocalDateTime createdAt
){
}
