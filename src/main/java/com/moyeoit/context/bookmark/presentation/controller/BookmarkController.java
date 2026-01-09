package com.moyeoit.context.bookmark.presentation.controller;

import com.moyeoit.context.bookmark.presentation.request.BookmarkCreateRequest;
import com.moyeoit.context.bookmark.presentation.response.BookmarkResponse;
import com.moyeoit.context.bookmark.presentation.swagger.BookmarkApi;
import com.moyeoit.context.club.presentation.response.ClubListResponse;
import com.moyeoit.context.review.presentation.response.BlogReviewResponse;
import com.moyeoit.context.review.presentation.response.ReviewSummaryResponse;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/bookmarks")
public class BookmarkController implements BookmarkApi {

    @Override
    @GetMapping("/clubs")
    public ApiResponse<Page<ClubListResponse>> getBookmarkedClubs(@CurrentUser AccessUser user, Pageable pageable) {
        // TODO: Implement logic to fetch bookmarked clubs
        return ApiResponse.success(Page.empty(pageable));
    }

    @Override
    @GetMapping("/reviews/interview")
    public ApiResponse<Page<ReviewSummaryResponse>> getBookmarkedInterviewReviews(@CurrentUser AccessUser user, Pageable pageable) {
        // TODO: Implement logic to fetch bookmarked interview reviews
        return ApiResponse.success(Page.empty(pageable));
    }

    @Override
    @GetMapping("/reviews/activity")
    public ApiResponse<Page<ReviewSummaryResponse>> getBookmarkedActivityReviews(@CurrentUser AccessUser user, Pageable pageable) {
        // TODO: Implement logic to fetch bookmarked activity reviews
        return ApiResponse.success(Page.empty(pageable));
    }

    @Override
    @GetMapping("/reviews/blog")
    public ApiResponse<Page<BlogReviewResponse>> getBookmarkedBlogReviews(@CurrentUser AccessUser user, Pageable pageable) {
        // TODO: Implement logic to fetch bookmarked blog reviews
        return ApiResponse.success(Page.empty(pageable));
    }

    @Override
    @PostMapping
    public ApiResponse<BookmarkResponse> toggleBookmark(@CurrentUser AccessUser user, @RequestBody BookmarkCreateRequest request) {
        // TODO: Implement logic to toggle bookmark based on request.getType()
        return ApiResponse.success(new BookmarkResponse(true, request.getType(), request.getTargetId()));
    }
}
