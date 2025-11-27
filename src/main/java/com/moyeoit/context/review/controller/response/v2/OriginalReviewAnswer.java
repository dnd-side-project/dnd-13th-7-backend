package com.moyeoit.context.review.controller.response.v2;

import com.moyeoit.context.review.controller.response.QuestionResponse;
import com.moyeoit.context.review.domain.enums.AnswerType;
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