package com.moyeoit.global.response.review;

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

        // headLine, likeCount, commentCount는 추후 구현 예정
        return new PremiumReviewListResponse(
                info.getReviewId(),
                info.getTitle(),
                info.getImageUrl(),
                null,       // headLine
                identifier,
                null,       // likeCount
                null        // commentCount
        );
    }
}
