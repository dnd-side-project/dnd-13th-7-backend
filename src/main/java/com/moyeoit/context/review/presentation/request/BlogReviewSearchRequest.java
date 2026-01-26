package com.moyeoit.context.review.presentation.request;

import com.moyeoit.context.review.domain.enums.ReviewSort;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogReviewSearchRequest {

    private String title;
    private Long clubId;
    private Long jobId;
    private Integer generation;

    private ReviewSort sort = ReviewSort.LATEST;

}