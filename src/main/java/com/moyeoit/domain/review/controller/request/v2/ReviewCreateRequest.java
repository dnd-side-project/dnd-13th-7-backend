package com.moyeoit.domain.review.controller.request.v2;

import com.moyeoit.domain.review.domain.enums.ReviewElementCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequest {

    private ReviewElementCategory category;

    private Long clubId;
    private Integer generation;
    private Long jobId;

    private List<ReviewAnswerCreateRequest> answers;

}
