package com.moyeoit.domain.review.controller.response;

import com.moyeoit.domain.app_user.service.dto.AppUserDto;
import com.moyeoit.domain.app_user.service.dto.JobDto;
import com.moyeoit.domain.club.dto.ClubSlimDto;
import com.moyeoit.domain.review.domain.BasicReview;
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
public class BasicReviewResponse implements ReviewResponse {

    private Long id;
    private ClubSlimDto club;
    private Integer cohort;
    private JobDto job;
    private AppUserDto user;
    private ReviewCategory reviewCategory;
    private LocalDateTime createDazte;
    private LocalDateTime updateDate;
    private List<AnswerResponse> details;

    public static ReviewResponse from(BasicReview review, List<AnswerResponse> answer) {
        return new BasicReviewResponse(review.getId(),
                ClubSlimDto.from(review.getClub()),
                review.getCohort(),
                JobDto.of(review.getJob()),
                AppUserDto.of(review.getUser()),
                review.getReviewCategory(),
                review.getCreateDate(),
                review.getUpdateDate(),
                answer
        );
    }

}