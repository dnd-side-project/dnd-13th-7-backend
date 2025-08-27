package com.moyeoit.domain.review.controller.response;

import com.moyeoit.domain.review.dto.ReviewQueryDto.PremiumReviewInfo;
import java.util.List;

public record PremiumReviewListResponse(
        Long reviewId,
        String title,
        String imageUrl,
        String headLine,
        List<String> identifier,
        Integer likeCount,
        Integer commentCount
) {
    public static PremiumReviewListResponse from(PremiumReviewInfo info) {
        List<String> identifier = List.of(
                info.getClubName(),
                info.getCohort() + "기",
                info.getPart()
        );


        return new PremiumReviewListResponse(
                info.getReviewId(),
                info.getTitle(),
                info.getImageUrl(),
                "헤드라인",
                identifier,
                info.getLikeCount(),
                info.getCommentCount()
        );
    }
}
