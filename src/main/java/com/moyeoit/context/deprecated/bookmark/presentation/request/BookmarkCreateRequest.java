package com.moyeoit.context.deprecated.bookmark.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "북마크 생성 요청")
public class BookmarkCreateRequest {

    @Schema(description = "북마크할 대상의 ID (동아리 ID 또는 리뷰 ID)", example = "1")
    private Long targetId;

    @Schema(description = "북마크 타입 (CLUB: 동아리, INTERVIEW_REVIEW: 서류/면접 후기, ACTIVITY_REVIEW: 활동 후기, BLOG_REVIEW: 블로그 후기)", example = "CLUB")
    private BookmarkType type;

}
