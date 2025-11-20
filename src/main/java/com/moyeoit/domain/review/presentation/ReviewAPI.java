package com.moyeoit.domain.review.presentation;

import com.moyeoit.domain.review.presentation.request.ReviewCreateRequest;
import com.moyeoit.domain.review.presentation.request.ReviewSearchRequest;
import com.moyeoit.domain.review.presentation.response.ReviewSummaryResponse;
import com.moyeoit.domain.review.presentation.response.ReviewView;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ReviewAPI {

    @Operation(summary = "리뷰 조회 API", description = "리뷰 ID를 기반으로 리뷰를 조회합니다.")
    ApiResponse<ReviewView> getReview(@PathVariable Long reviewId);

    @Operation(summary = "리뷰 생성 API", description = "리뷰를 생성합니다.")
    void createReview(@RequestBody ReviewCreateRequest request,
                      @CurrentUser AccessUser user);

    @Operation(summary = "리뷰 검색 API", description = "다양한 조건으로 리뷰를 검색합니다.")
    ApiResponse<Page<ReviewSummaryResponse>> searchReview(@ModelAttribute ReviewSearchRequest request,
                                                          @PageableDefault Pageable pageable);
}
