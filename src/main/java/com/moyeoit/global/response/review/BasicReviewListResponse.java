package com.moyeoit.global.response.review;

import java.util.List;

public record BasicReviewListResponse(
        Long reviewId,
        String profileImgUrl,
        String nickname,
        String clubName,
        String cohort,
        String part,
        Double rate,
        String createdAt,
        String category,
        Integer likeCount,
        String oneLineComment,
        String impressiveContentPreview,
        List<QAPreview> qaPreviews
) {
    public record QAPreview(
            String questionTitle,
            String answerValue
    ){
        //todo: from 메소드 추가
    }
}
