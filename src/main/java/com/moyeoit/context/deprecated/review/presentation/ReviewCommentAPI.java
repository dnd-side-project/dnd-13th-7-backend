package com.moyeoit.context.deprecated.review.presentation;

import com.moyeoit.context.deprecated.review.presentation.request.comment.ReviewCommentCreateRequest;
import com.moyeoit.context.deprecated.review.presentation.request.comment.ReviewCommentResponse;
import com.moyeoit.context.deprecated.review.presentation.request.comment.ReviewCommentUpdateRequest;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ReviewCommentAPI {

    @Operation(summary = "리뷰 댓글 조회 API", description = "리뷰 ID를 기반으로 리뷰 댓글 목록을 조회합니다.")
    ResponseEntity<List<ReviewCommentResponse>> getReviewComments(@PathVariable Long reviewId);

    @Operation(summary = "리뷰 댓글 생성 API", description = "리뷰 댓글을 생성합니다.")
    ResponseEntity<Void> createReviewComment(@RequestBody ReviewCommentCreateRequest req,
                                             @CurrentUser AccessUser user);

    @Operation(summary = "리뷰 댓글 삭제 API", description = "리뷰 댓글을 삭제합니다.")
    ResponseEntity<Void> deleteReviewComment(@PathVariable Long commentId,
                                             @CurrentUser AccessUser user);

    @Operation(summary = "리뷰 댓글 수정 API", description = "리뷰 댓글을 수정합니다.")
    ResponseEntity<Void> updateReviewComment(@PathVariable Long commentId,
                                             @RequestBody ReviewCommentUpdateRequest req,
                                             @CurrentUser AccessUser user);


}
