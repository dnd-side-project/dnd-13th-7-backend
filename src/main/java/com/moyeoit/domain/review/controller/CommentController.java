package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.CommentCreateRequest;
import com.moyeoit.domain.review.controller.response.CommentResponse;
import com.moyeoit.domain.review.service.CommentService;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/premium/{premiumReviewId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getCommentOfPremiumReview(
            @PathVariable Long premiumReviewId) {
        List<CommentResponse> detailResponses = commentService.getCommentOfPremiumReview(premiumReviewId);
        return ResponseEntity.ok(ApiResponse.success(detailResponses));
    }

    @PostMapping("/premium/{premiumReviewId}")
    public ResponseEntity<ApiResponse<CommentResponse>> createCommentOfPremiumReview(
            @PathVariable Long premiumReviewId,
            @RequestBody CommentCreateRequest request,
            @Parameter(hidden = true) @CurrentUser AccessUser user) {
        CommentResponse response = commentService.createCommentOfPremiumReview(premiumReviewId, user.getId(), request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}