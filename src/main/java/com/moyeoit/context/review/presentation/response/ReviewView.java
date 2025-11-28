package com.moyeoit.context.review.presentation.response;

import com.moyeoit.context.club.dto.ClubWithNameAndImageUrlDto;
import com.moyeoit.context.review.controller.response.v2.ReviewAnswerResponse;
import com.moyeoit.context.review.domain.enums.ReviewResult;
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
public class ReviewView {

    private String title;
    private Double rate;
    private ReviewResult result;
    private JobDto job;
    private ClubWithNameAndImageUrlDto club;
    private Integer generation;
    private Long likeCount;
    private Long commentCount;
    private List<ReviewAnswerResponse> answers;

}
