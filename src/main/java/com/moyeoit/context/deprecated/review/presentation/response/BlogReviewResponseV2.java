package com.moyeoit.context.deprecated.review.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "블로그 후기 응답")
public class BlogReviewResponseV2 {

    @Schema(description = "리뷰 ID", example = "1")
    private Long reviewId;

    @Schema(description = "동아리 이름", example = "DND")
    private String clubName;

    @Schema(description = "직무 이름", example = "백엔드 개발자")
    private String jobName;

    @Schema(description = "리뷰 제목", example = "활동 후기입니다.")
    private String title;

    @Schema(description = "블로그 이름", example = "바키의 블로그")
    private String blogName;

    @Schema(description = "기수", example = "13")
    private Integer generation;

    @Schema(description = "블로그 URL", example = "https://velog.io/@user/review")
    private String blogUrl;;

    @Schema(description = "블로그 썸네일 URL", example = "https://example.com")
    private String imageUrl;

    @Schema(description = "생성일", example = "2024-01-01T00:00:00")
    private LocalDateTime createdDate;

    @Schema(description = "수정일", example = "2024-01-01T00:00:00")
    private LocalDateTime updateDate;

    @Schema(description = "북마크 여부", example = "true")
    private Boolean isBookmarked;

}