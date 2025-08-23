package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.response.ReviewLikeResponse;
import com.moyeoit.domain.review.service.ReviewLikeService;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/like")
@RequiredArgsConstructor
public class ReviewLikeController {

    private final ReviewLikeService reviewLikeService;

    @PostMapping("/{reviewId}/{reviewType}")
    public ApiResponse<ReviewLikeResponse> likeReview(
            @PathVariable Long reviewId,
            @PathVariable String reviewType,
            @Parameter(hidden = true) @CurrentUser AccessUser user){
        return ApiResponse.success("리뷰 좋아요 토글을 성공하였습니다.",reviewLikeService.toggleLike(reviewId,reviewType, user.getId()));
    }

}
