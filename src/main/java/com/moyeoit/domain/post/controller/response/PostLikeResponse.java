package com.moyeoit.domain.post.controller.response;

public record PostLikeResponse(
        Long postId,
        boolean liked,
        int likeCount
) {
}
