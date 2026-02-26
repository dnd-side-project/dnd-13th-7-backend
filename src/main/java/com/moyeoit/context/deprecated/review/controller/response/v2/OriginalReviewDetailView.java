package com.moyeoit.context.deprecated.review.controller.response.v2;

import com.moyeoit.context.club.application.dto.ClubWithNameAndImageUrlDto;
import com.moyeoit.context.deprecated.review.domain.enums.ReviewResult;
import com.moyeoit.context.user.service.dto.JobDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OriginalReviewDetailView {

    private String title;
    private Double rate;
    private ReviewResult result;
    private JobDto job;
    private ClubWithNameAndImageUrlDto club;
    private Integer generation;
    private Long likeCount;
    private Long commentCount;
    private List<OriginalReviewAnswer> answers;

}