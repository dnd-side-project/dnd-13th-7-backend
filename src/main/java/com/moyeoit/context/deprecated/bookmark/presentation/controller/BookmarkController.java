package com.moyeoit.context.deprecated.bookmark.presentation.controller;

import com.moyeoit.context.deprecated.bookmark.infra.query.BookmarkQueryRepository;
import com.moyeoit.context.deprecated.bookmark.presentation.request.BookmarkCreateRequest;
import com.moyeoit.context.deprecated.bookmark.presentation.request.BookmarkType;
import com.moyeoit.context.deprecated.bookmark.presentation.response.BookmarkResponse;
import com.moyeoit.context.deprecated.bookmark.presentation.swagger.BookmarkApi;
import com.moyeoit.context.deprecated.bookmark.service.BookmarkService;
import com.moyeoit.context.club.presentation.response.ClubListResponse;
import com.moyeoit.context.deprecated.review.presentation.response.BlogReviewResponse;
import com.moyeoit.context.deprecated.review.presentation.response.ReviewSummaryResponse;
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

    private final BookmarkService bookmarkService;
    private final BookmarkQueryRepository bookmarkQueryRepository;

    @Override
    @GetMapping("/clubs")
    public ApiResponse<Page<ClubListResponse>> getBookmarkedClubs(@CurrentUser AccessUser user, Pageable pageable) {
        return ApiResponse.success(bookmarkQueryRepository.findBookmarkedClubs(user.getId(), pageable));
    }

    @Override
    @GetMapping("/reviews/interview")
    public ApiResponse<Page<ReviewSummaryResponse>> getBookmarkedInterviewReviews(@CurrentUser AccessUser user, Pageable pageable) {
        return ApiResponse.success(bookmarkQueryRepository.findBookmarkedReviews(user.getId(), BookmarkType.INTERVIEW_REVIEW, pageable));
    }

    @Override
    @GetMapping("/reviews/activity")
    public ApiResponse<Page<ReviewSummaryResponse>> getBookmarkedActivityReviews(@CurrentUser AccessUser user, Pageable pageable) {
        return ApiResponse.success(bookmarkQueryRepository.findBookmarkedReviews(user.getId(), BookmarkType.ACTIVITY_REVIEW, pageable));
    }

    @Override
    @GetMapping("/reviews/blog")
    public ApiResponse<Page<BlogReviewResponse>> getBookmarkedBlogReviews(@CurrentUser AccessUser user, Pageable pageable) {
        return ApiResponse.success(bookmarkQueryRepository.findBookmarkedBlogReviews(user.getId(), pageable));
    }

    @Override
    @PostMapping
    public ApiResponse<BookmarkResponse> toggleBookmark(@CurrentUser AccessUser user, @RequestBody BookmarkCreateRequest request) {
        return ApiResponse.success(bookmarkService.toggleBookmark(user.getId(), request));
    }
}
