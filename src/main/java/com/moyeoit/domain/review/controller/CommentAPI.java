package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.CommentCreateRequest;
import com.moyeoit.domain.review.controller.response.CommentResponse;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CommentAPI {

    @Operation(summary = "프리미엄 후기 댓글 조회 API", description = "프리미엄 후기 댓글을 조회합니다.")
    ResponseEntity<ApiResponse<List<CommentResponse>>> getCommentOfPremiumReview(
            @PathVariable Long premiumReviewId);

    @Operation(summary = "프리미엄 후기 댓글 생성 API", description = "프리미엄 후기 댓글을 생성합니다.")
    ResponseEntity<ApiResponse<CommentResponse>> createCommentOfPremiumReview(
            @PathVariable Long premiumReviewId,
            @RequestBody CommentCreateRequest request,
            @Parameter(hidden = true) @CurrentUser AccessUser user);
}
