package com.moyeoit.domain.review.controller.response;

import com.moyeoit.domain.review.dto.PremiumReviewDetailDto;
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
    public static PremiumReviewListResponse from(PremiumReviewInfo info, List<PremiumReviewDetailDto> details) {

        String jobFamily = switch (info.getPart()){
            case String s when s.contains("개발자") -> "개발";
            case String s when s.contains("디자이너") -> "디자인";
            case String s when s.contains("PM") -> "기획";
            default -> "기타";
        };

        List<String> identifier = List.of(
                info.getClubName(),
                info.getCohort() + "기",
                jobFamily
        );

        return new PremiumReviewListResponse(
                info.getReviewId(),
                info.getTitle(),
                info.getImageUrl(),
                details.getFirst().getAnswer(),
                identifier,
                info.getLikeCount(),
                info.getCommentCount()
        );
    }
}
