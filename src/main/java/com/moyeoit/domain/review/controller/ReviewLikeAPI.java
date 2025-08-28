package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.response.ReviewLikeResponse;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PathVariable;

public interface ReviewLikeAPI {

    @Operation(summary = "리뷰 Toggle API", description = "리뷰 좋아요에 대한 Toggle 처리를 진행합니다.")
    ApiResponse<ReviewLikeResponse> likeReview(
            @PathVariable Long reviewId,
            @PathVariable String reviewType,
            @Parameter(hidden = true) @CurrentUser AccessUser user);

}
