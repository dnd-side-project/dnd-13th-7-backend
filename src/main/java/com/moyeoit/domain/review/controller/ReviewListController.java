package com.moyeoit.domain.review.controller;

import com.moyeoit.domain.review.controller.request.ReviewPagingRequest;
import com.moyeoit.domain.review.service.ReviewListService;
import com.moyeoit.global.response.ApiResponse;
import com.moyeoit.global.response.review.BasicReviewListResponse;
import com.moyeoit.global.response.review.PremiumReviewListResponse;
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
public class ReviewListController {

    private final ReviewListService reviewListService;

    @GetMapping("/basic")
    public ApiResponse<Page<BasicReviewListResponse>> getBasicReviewList(
            @ModelAttribute ReviewPagingRequest request,
            @PageableDefault(size = 4, direction = Sort.Direction.DESC) Pageable pageable){
            return ApiResponse.success("기본 리뷰 목록 조회에 성공하였습니다.",reviewListService.findBasicReviewList(request,pageable));
    }

    @GetMapping("/premium")
    public ApiResponse<Page<PremiumReviewListResponse>> getPremiumReviewList(
            @ModelAttribute ReviewPagingRequest request,
            @PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable){
        return ApiResponse.success("기본 리뷰 목록 조회에 성공하였습니다.",reviewListService.findPremiumReviewList(request,pageable));
    }

}
