package com.moyeoit.domain.review.dto;

import com.moyeoit.domain.review.domain.PremiumReviewDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PremiumReviewDetailDto {
    private Long reviewId;
    private String answer;

    public PremiumReviewDetailDto(PremiumReviewDetail detail) {
        this.reviewId = detail.getReview().getId();
        this.answer = detail.getValue();
    }
}