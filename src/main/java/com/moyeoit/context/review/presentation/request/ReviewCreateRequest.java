package com.moyeoit.context.review.presentation.request;

import com.moyeoit.context.review.domain.enums.ReviewCategory;
import com.moyeoit.context.review.domain.enums.ReviewResult;
import com.moyeoit.context.review.presentation.request.answer.ReviewAnswerCreateRequest;
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

    private String title;
    private ReviewCategory category;
    private Double rate;
    private ReviewResult result;
    private Integer generation;

    private Long clubId;
    private Long jobId;

    private List<ReviewAnswerCreateRequest> answers;

}
