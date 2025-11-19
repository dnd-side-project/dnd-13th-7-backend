package com.moyeoit.domain.review.controller.response.v2;

import com.moyeoit.domain.club.dto.ClubWithNameAndImageUrlDto;
import com.moyeoit.domain.review.domain.enums.ReviewResult;
import com.moyeoit.domain.user.service.dto.JobDto;
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
    private List<OriginalReviewAnswer> answers;

}