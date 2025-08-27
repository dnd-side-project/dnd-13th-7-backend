package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.BasicReviewCreateRequest;
import com.moyeoit.domain.review.controller.request.MyReviewSearchRequest;
import com.moyeoit.domain.review.controller.request.PremiumReviewCreateRequest;
import com.moyeoit.domain.review.controller.response.PremiumReviewResponse;
import com.moyeoit.domain.review.controller.response.ReviewResponse;
import com.moyeoit.domain.review.service.BasicReviewService;
import com.moyeoit.domain.review.service.PremiumReviewService;
import com.moyeoit.domain.review.service.ReviewService;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final BasicReviewService basicReviewService;
    private final PremiumReviewService premiumReviewService;

    @GetMapping("/premium/{premiumReviewId}")
    public ResponseEntity<ApiResponse<PremiumReviewResponse>> getPremiumReview(@PathVariable Long premiumReviewId) {
        PremiumReviewResponse response = premiumReviewService.getPremiumReview(premiumReviewId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/basic")
    public ResponseEntity<?> createBasicReview(@RequestBody BasicReviewCreateRequest request,
                                               @Parameter(hidden = true) @CurrentUser AccessUser user) {
        basicReviewService.createBasicReview(request, user.getId());
        return ResponseEntity.ok("");
    }

    @PostMapping("/premium")
    public ResponseEntity<?> createPremiumReview(@RequestBody PremiumReviewCreateRequest reviewCreateRequest,
                                                 @Parameter(hidden = true) @CurrentUser AccessUser user) {
        premiumReviewService.createPremiumReview(reviewCreateRequest, user.getId());
        return ResponseEntity.ok("");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ReviewResponse>>> getReview(@ModelAttribute MyReviewSearchRequest request,
                                                                       @ParameterObject Pageable pageable,
                                                                       @Parameter(hidden = true) @CurrentUser AccessUser user) {
        Page<ReviewResponse> response = reviewService.getReview(request, user.getId(), pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
