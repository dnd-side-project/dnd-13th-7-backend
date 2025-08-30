package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.BasicReviewCreateRequest;
import com.moyeoit.domain.review.controller.request.PremiumReviewCreateRequest;
import com.moyeoit.domain.review.controller.response.PremiumReviewResponse;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ReviewAPI {

    @Operation(summary = "프리미엄 상세 조회 API", description = "프리미엄 후기 ID로 프리미엄 후기를 상세 조회 합니다.")
    ResponseEntity<ApiResponse<PremiumReviewResponse>> getPremiumReview(@PathVariable Long premiumReviewId);

    @Operation(summary = "일반 후기 작성 API", description = "일반 후기를 작성합니다. (Basic Review)")
    ResponseEntity<?> createBasicReview(@RequestBody BasicReviewCreateRequest request,
                                        @Parameter(hidden = true) @CurrentUser AccessUser user);

    @Operation(summary = "프리미엄 후기 작성 API", description = "프리미엄 후기를 작성합니다. (Premium Review)")
    ResponseEntity<?> createPremiumReview(@RequestBody PremiumReviewCreateRequest reviewCreateRequest,
                                          @Parameter(hidden = true) @CurrentUser AccessUser user);

}
