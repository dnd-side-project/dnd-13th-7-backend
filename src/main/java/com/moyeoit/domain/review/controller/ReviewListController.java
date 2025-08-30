package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.ReviewPagingRequest;
import com.moyeoit.domain.review.controller.response.BasicReviewListResponse;
import com.moyeoit.domain.review.controller.response.PremiumReviewListResponse;
import com.moyeoit.domain.review.service.ReviewListService;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/reviews")
@RequiredArgsConstructor
@Tag(name = "후기 목록 API", description = "후기 탐색에 사용되는 API 입니다.")
public class ReviewListController implements ReviewListAPI {

    private final ReviewListService reviewListService;

    @GetMapping("/basic")
    public ApiResponse<Page<BasicReviewListResponse>> getBasicReviewList(
            @ModelAttribute ReviewPagingRequest request,
            @PageableDefault(size = 4, direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.success("기본 리뷰 목록 조회에 성공하였습니다.", reviewListService.findBasicReviewList(request, pageable));
    }

    @GetMapping("/premium")
    public ApiResponse<Page<PremiumReviewListResponse>> getPremiumReviewList(
            @ModelAttribute ReviewPagingRequest request,
            @PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.success("기본 리뷰 목록 조회에 성공하였습니다.", reviewListService.findPremiumReviewList(request, pageable));
    }

}
