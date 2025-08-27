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
public class PremiumReviewResponse implements ReviewResponse {

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
    private List<AnswerResponse> details;

    public static ReviewResponse from(PremiumReview review, List<AnswerResponse> answerResponses) {
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
                answerResponses
        );
    }

    public static PremiumReviewResponse implFrom(PremiumReview review, List<AnswerResponse> answerResponses) {
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
                answerResponses
        );
    }

}
