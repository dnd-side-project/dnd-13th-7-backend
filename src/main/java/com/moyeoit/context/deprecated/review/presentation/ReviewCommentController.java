package com.moyeoit.context.deprecated.review.presentation;

import com.moyeoit.context.deprecated.review.presentation.request.comment.ReviewCommentCreateRequest;
import com.moyeoit.context.deprecated.review.presentation.request.comment.ReviewCommentResponse;
import com.moyeoit.context.deprecated.review.presentation.request.comment.ReviewCommentUpdateRequest;
import com.moyeoit.context.deprecated.review.service.ReviewCommentService;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/review/comment")
@Slf4j
@Tag(name = "리뷰 댓글 API", description = "리뷰 댓글을 작성하고 관리하는 API 입니다.")
public class ReviewCommentController implements ReviewCommentAPI {

    private final ReviewCommentService reviewCommentService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<List<ReviewCommentResponse>> getReviewComments(@PathVariable Long reviewId) {
        var response = reviewCommentService.getReviewComments(reviewId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> createReviewComment(@RequestBody ReviewCommentCreateRequest req,
                                                    @CurrentUser AccessUser user) {
        reviewCommentService.createReviewComment(req, user.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteReviewComment(@PathVariable Long commentId,
                                                    @CurrentUser AccessUser user) {
        reviewCommentService.deleteReviewComment(commentId, user.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateReviewComment(@PathVariable Long commentId,
                                                    @RequestBody ReviewCommentUpdateRequest req,
                                                    @CurrentUser AccessUser user) {
        reviewCommentService.updateReviewComment(commentId, req, user.getId());
        return ResponseEntity.ok().build();
    }

}