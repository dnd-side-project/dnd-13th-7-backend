package com.moyeoit.context.review.presentation.request;

import com.moyeoit.context.review.domain.enums.ReviewCategory;
import com.moyeoit.context.review.domain.enums.ReviewResult;
import com.moyeoit.context.review.domain.enums.ReviewSort;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSearchRequest {

    private String title;
    private ReviewCategory category;
    private Long clubId;
    private Integer generation;
    private ReviewResult result;
    private Long userId;

    private ReviewSort sort = ReviewSort.LATEST;

}