package com.moyeoit.domain.review.controller.response;

import com.moyeoit.domain.review.domain.DisplayQuestion;
import com.moyeoit.domain.review.domain.ReviewCategory;
import com.moyeoit.domain.review.domain.ReviewType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisplayQuestionResponse {

    private Long id;
    private QuestionResponse question;
    private ReviewType reviewType;
    private ReviewCategory reviewCategory;
    private Integer sort;

    public static DisplayQuestionResponse from(DisplayQuestion displayQuestion) {
        return new DisplayQuestionResponse(displayQuestion.getId(),
                QuestionResponse.from(displayQuestion.getQuestion()),
                displayQuestion.getReviewType(),
                displayQuestion.getReviewCategory(),
                displayQuestion.getSort());
    }

}