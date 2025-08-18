package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.controller.request.ReviewPagingRequest;
import com.moyeoit.global.response.review.BasicReviewListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicReviewRepositoryCustom {
    Page<BasicReviewListResponse> findBasicReviewByRequest(ReviewPagingRequest request, Pageable pageable);
}
