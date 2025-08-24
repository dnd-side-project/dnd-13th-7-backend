package com.moyeoit.domain.review.controller.response;

import com.moyeoit.domain.app_user.service.dto.AppUserDto;
import com.moyeoit.domain.app_user.service.dto.JobDto;
import com.moyeoit.domain.club.dto.ClubSlimDto;
import com.moyeoit.domain.review.domain.PremiumReview;
import com.moyeoit.domain.review.domain.ResultType;
import com.moyeoit.domain.review.domain.ReviewCategory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PremiumReviewResponse {

    private Long id;
    private ClubSlimDto club;
    private Integer cohort;
    private JobDto job;
    private AppUserDto user;
    private String imageUrl;
    private String title;
    private ResultType resultType;
    private ReviewCategory reviewCategory;
    private LocalDateTime createDazte;
    private LocalDateTime updateDate;
    private List<PremiumReviewDetailResponse> details;

    public static PremiumReviewResponse from(PremiumReview review) {
        List<PremiumReviewDetailResponse> premiumReviewDetailResponses = review.getPremiumReviewDetails().stream()
                .map(PremiumReviewDetailResponse::from)
                .toList();

        return new PremiumReviewResponse(review.getId(),
                ClubSlimDto.from(review.getClub()),
                review.getCohort(),
                JobDto.of(review.getJob()),
                AppUserDto.of(review.getUser()),
                review.getImageUrl(),
                review.getTitle(),
                review.getResultType(),
                review.getReviewCategory(),
                review.getCreateDate(),
                review.getUpdateDate(),
                premiumReviewDetailResponses
        );
    }

    // TODO details


}
