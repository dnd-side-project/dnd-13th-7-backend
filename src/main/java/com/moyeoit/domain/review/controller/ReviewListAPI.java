package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.ReviewPagingRequest;
import com.moyeoit.domain.review.controller.response.BasicReviewListResponse;
import com.moyeoit.domain.review.controller.response.PremiumReviewListResponse;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface ReviewListAPI {

    @Operation(summary = "일반 후기 목록 조회 API", description = "일반 후기 목록을 조회합니다.")
    ApiResponse<Page<BasicReviewListResponse>> getBasicReviewList(
            @ModelAttribute ReviewPagingRequest request,
            @PageableDefault(size = 4, direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "프리미엄 후기 목록 조회 API", description = "프리미엄 후기 목록을 조회합니다.")
    ApiResponse<Page<PremiumReviewListResponse>> getPremiumReviewList(
            @ModelAttribute ReviewPagingRequest request,
            @PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable);
}
