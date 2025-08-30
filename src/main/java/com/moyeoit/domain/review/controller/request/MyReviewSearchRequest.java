package com.moyeoit.domain.review.controller.request;

import com.moyeoit.domain.review.domain.ReviewCategory;
import com.moyeoit.domain.review.domain.ReviewType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 내 리뷰 조회 Request 객체
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyReviewSearchRequest {


    private ReviewType reviewType;

    private ReviewCategory reviewCategory;

}
