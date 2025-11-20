package com.moyeoit.domain.post.controller.response;

public record PopularPostResponse(
        Long postId,
        String title,
        String excerpt,
        Long categoryId,
        String categoryName,
        Integer likeCount,
        Integer commentCount
) {
}
