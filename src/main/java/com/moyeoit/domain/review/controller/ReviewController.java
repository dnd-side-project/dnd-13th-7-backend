package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.BasicReviewCreateRequest;
import com.moyeoit.domain.review.controller.request.PremiumReviewCreateRequest;
import com.moyeoit.domain.review.controller.response.PremiumReviewResponse;
import com.moyeoit.domain.review.service.BasicReviewService;
import com.moyeoit.domain.review.service.PremiumReviewService;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/review")
@Tag(name = "후기 API", description = "일반/프리미엄 후기를 작성하고 관리하는 API 입니다.")
public class ReviewController implements ReviewAPI {

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
    public ResponseEntity<ApiResponse<?>> createPremiumReview(@RequestBody PremiumReviewCreateRequest reviewCreateRequest,
                                                 @Parameter(hidden = true) @CurrentUser AccessUser user) {
        Long savedReviewId = premiumReviewService.createPremiumReview(reviewCreateRequest, user.getId());
        return ResponseEntity.ok(ApiResponse.success("리뷰 저장에 성공하였습니다", Map.of("Saved Review Id",savedReviewId)));
    }

}
