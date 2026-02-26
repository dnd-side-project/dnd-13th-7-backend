package com.moyeoit.context.deprecated.review.controller.response.v2;

import com.moyeoit.context.deprecated.review.controller.response.QuestionResponse;
import com.moyeoit.context.deprecated.review.domain.enums.AnswerType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OriginalReviewAnswer {

    private Long id;
    private QuestionResponse question;
    private String value;
    private AnswerType answerType;

}