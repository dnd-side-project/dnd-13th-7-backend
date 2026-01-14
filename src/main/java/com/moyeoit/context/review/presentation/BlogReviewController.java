package com.moyeoit.context.review.presentation;

import com.moyeoit.context.review.presentation.request.BlogReviewSearchRequest;
import com.moyeoit.context.review.service.BlogReviewService;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/blog-review")
@Tag(name = "블로그 리뷰 API", description = "블로그 리뷰를 관리/조회하는 API 입니다.")
public class BlogReviewController {

    private final BlogReviewService blogReviewService;

    @GetMapping("/search")
    public ApiResponse<Page<?>> searchBlogReview(@ModelAttribute BlogReviewSearchRequest request,
                                                 @PageableDefault Pageable pageable) {
        return ApiResponse.success(blogReviewService.search(request, pageable));
    }

}
