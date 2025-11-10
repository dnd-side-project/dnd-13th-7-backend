package com.moyeoit.domain.review.controller.response.v2;

import com.moyeoit.domain.club.dto.ClubWithNameAndImageUrlDto;
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
public class ReviewSummary {

    private ClubWithNameAndImageUrlDto club;
    private Integer generation;
    private JobDto job;
//    private Double rate;
//    private String rateTitle;

    private List<ReviewAnswerSummary> answers;
//    private String textSummary;

}