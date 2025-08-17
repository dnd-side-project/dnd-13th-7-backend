package com.moyeoit.domain.review.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

public class ReviewQueryDto {

    @Getter
    public static class PremiumReviewInfo {
        private final Long reviewId;
        private final String title;
        private final String imageUrl;
        private final String clubName;
        private final Integer cohort;
        private final String part;

        @QueryProjection
        public PremiumReviewInfo(Long reviewId, String title, String imageUrl, String clubName, Integer cohort, String part) {
            this.reviewId = reviewId;
            this.title = title;
            this.imageUrl = imageUrl;
            this.clubName = clubName;
            this.cohort = cohort;
            this.part = part;
        }
    }
}
