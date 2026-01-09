package com.moyeoit.context.bookmark.presentation.swagger;

import com.moyeoit.context.bookmark.presentation.request.BookmarkCreateRequest;
import com.moyeoit.context.bookmark.presentation.response.BookmarkResponse;
import com.moyeoit.context.club.presentation.response.ClubListResponse;
import com.moyeoit.context.review.presentation.response.BlogReviewResponse;
import com.moyeoit.context.review.presentation.response.ReviewSummaryResponse;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "북마크 API", description = "사용자가 등록한 북마크 관련 API 입니다.")
public interface BookmarkApi {

    @Operation(summary = "북마크한 동아리 목록 조회", description = "사용자가 북마크한 동아리 목록을 조회합니다.")
    ApiResponse<Page<ClubListResponse>> getBookmarkedClubs(
            @Parameter(hidden = true) @CurrentUser AccessUser user,
            @PageableDefault(size = 12) Pageable pageable
    );

    @Operation(summary = "북마크한 서류/면접 후기 목록 조회", description = "사용자가 북마크한 서류/면접 후기 목록을 조회합니다.")
    ApiResponse<Page<ReviewSummaryResponse>> getBookmarkedInterviewReviews(
            @Parameter(hidden = true) @CurrentUser AccessUser user,
            @PageableDefault(size = 6) Pageable pageable
    );

    @Operation(summary = "북마크한 활동 후기 목록 조회", description = "사용자가 북마크한 활동 후기 목록을 조회합니다.")
    ApiResponse<Page<ReviewSummaryResponse>> getBookmarkedActivityReviews(
            @Parameter(hidden = true) @CurrentUser AccessUser user,
            @PageableDefault(size = 6) Pageable pageable
    );

    @Operation(summary = "북마크한 블로그 후기 목록 조회", description = "사용자가 북마크한 블로그 후기 목록을 조회합니다.")
    ApiResponse<Page<BlogReviewResponse>> getBookmarkedBlogReviews(
            @Parameter(hidden = true) @CurrentUser AccessUser user,
            @PageableDefault(size = 4) Pageable pageable
    );

    @Operation(summary = "북마크 토글", description = "북마크를 등록하거나 취소합니다.")
    ApiResponse<BookmarkResponse> toggleBookmark(
            @Parameter(hidden = true) @CurrentUser AccessUser user,
            @RequestBody BookmarkCreateRequest request
    );
}
