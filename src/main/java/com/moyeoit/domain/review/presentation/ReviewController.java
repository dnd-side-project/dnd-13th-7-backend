package com.moyeoit.domain.review.presentation;

import com.moyeoit.domain.review.presentation.request.ReviewCreateRequest;
import com.moyeoit.domain.review.presentation.request.ReviewSearchRequest;
import com.moyeoit.domain.review.presentation.response.ReviewSummaryResponse;
import com.moyeoit.domain.review.presentation.response.ReviewView;
import com.moyeoit.domain.review.service.ReviewLikeService;
import com.moyeoit.domain.review.service.ReviewService;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/review")
@Tag(name = "리뷰 API", description = "리뷰를 작성하고 관리하는 API 입니다.")
public class ReviewController implements ReviewAPI {

    private final ReviewService reviewService;
    private final ReviewLikeService reviewLikeService;

    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewView> getReview(@PathVariable Long reviewId) {
        ReviewView response = reviewService.getReview(reviewId);
        return ApiResponse.success(response);
    }

    @PostMapping
    public void createReview(@RequestBody ReviewCreateRequest request,
                             @CurrentUser AccessUser user) {
        reviewService.createReview(request, user.getId());
    }

    @PostMapping("/like/{reviewId}")
    public void likeReview(@RequestParam Long reviewId, @CurrentUser AccessUser user) {
        reviewLikeService.toggleLike(reviewId, user.getId());
    }

    @GetMapping("/search")
    public ApiResponse<Page<ReviewSummaryResponse>> searchReview(@ModelAttribute ReviewSearchRequest request,
                                                                 @PageableDefault Pageable pageable) {
        return ApiResponse.success(reviewService.search(request, pageable));
    }

}
