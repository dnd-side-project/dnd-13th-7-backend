package com.moyeoit.domain.review.presentation.request;

import com.moyeoit.domain.review.domain.enums.ReviewCategory;
import com.moyeoit.domain.review.domain.enums.ReviewResult;
import com.moyeoit.domain.review.domain.enums.ReviewSort;
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

    private ReviewSort sort = ReviewSort.LATEST;

}