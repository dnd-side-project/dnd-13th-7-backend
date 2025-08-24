package com.moyeoit.domain.review.controller.response;

public record ReviewLikeResponse(
        boolean liked,  // 현재 내가 좋아요 눌렀는지
        int likeCount
) {
}
