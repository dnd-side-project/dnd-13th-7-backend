package com.moyeoit.context.review.presentation.response;

import com.moyeoit.context.review.domain.enums.ReviewCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "리뷰 요약 응답")
public class ReviewSummaryResponse {

    @Schema(description = "리뷰 ID", example = "1")
    private Long reviewId;

    @Schema(description = "리뷰 카테고리", example = "서류")
    private ReviewCategory reviewCategory;

    @Schema(description = "동아리 이름", example = "코딩의 민족")
    private String clubName;

    @Schema(description = "기수", example = "13")
    private Integer generation;

    @Schema(description = "직무 이름", example = "백엔드 개발자")
    private String jobName;

    @Schema(description = "평점", example = "4.5")
    private Double rate;

    @Schema(description = "리뷰 제목", example = "활동 후기입니다.")
    private String title;

    @Schema(description = "답변 요약 목록")
    private List<ReviewChoiceSummary> answerSummaries;

    @Schema(description = "좋아요 수", example = "10")
    private Long likeCount;

    @Schema(description = "댓글 수", example = "5")
    private Long commentCount;

    @Schema(description = "북마크 여부", example = "true")
    private Boolean isBookmarked;

    public ReviewSummaryResponse(Long reviewId,
                                 ReviewCategory reviewCategory,
                                 String clubName,
                                 Integer generation,
                                 String jobName,
                                 Double rate,
                                 String title,
                                 List<String> choiceSummaries,
                                 Long likeCount,
                                 Long commentCount,
                                 Boolean isBookmarked) {
        this.reviewId = reviewId;
        this.reviewCategory = reviewCategory;
        this.clubName = clubName;
        this.generation = generation;
        this.jobName = jobName;
        this.rate = rate;
        this.title = title;
        this.answerSummaries = new ArrayList<>();
        if (choiceSummaries != null) {
            for (int i = 0; i < choiceSummaries.size(); i += 2) {
                if (i + 1 < choiceSummaries.size()) {
                    answerSummaries.add(new ReviewChoiceSummary(choiceSummaries.get(i), choiceSummaries.get(i + 1)));
                } else {
                    break;
                }
            }
        }
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.isBookmarked = isBookmarked;
    }

}
