package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.v2.ReviewCreateRequest;
import com.moyeoit.domain.review.controller.response.v2.ReviewMetadata;
import com.moyeoit.domain.review.controller.response.v2.ReviewSummary;
import com.moyeoit.domain.review.controller.response.v2.ReviewView;
import com.moyeoit.domain.review.service.ReviewServiceV2;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/review")
@Tag(name = "리뷰 API", description = "리뷰를 작성하고 관리하는 API 입니다.")
public class ReviewController {

    private final ReviewServiceV2 reviewServiceV2;

    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewView> getReview(@PathVariable Long reviewId) {
        ReviewView response = reviewServiceV2.getReview(reviewId);
        return ApiResponse.success(response);
    }

    @GetMapping
    public ApiResponse<List<ReviewSummary>> search() {
        List<ReviewSummary> response = reviewServiceV2.getReivews(null);
        return ApiResponse.success(response);
    }

    @PostMapping
    public void createReview(@RequestBody ReviewCreateRequest request,
                             @CurrentUser AccessUser user) {
        reviewServiceV2.createReview(request, user.getId());
    }

}
