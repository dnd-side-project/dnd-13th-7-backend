package com.moyeoit.context.deprecated.review.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "리뷰 답변 요약")
public class ReviewChoiceSummary {

    @Schema(description = "질문 요약", example = "지원 동기")
    private String questionTitleSummary;

    @Schema(description = "답변 요약", example = "성장하고 싶어서 지원했습니다.")
    private String answerSummary;

}
