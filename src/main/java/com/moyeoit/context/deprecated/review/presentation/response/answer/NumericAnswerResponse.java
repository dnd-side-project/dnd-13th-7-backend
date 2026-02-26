package com.moyeoit.context.deprecated.review.presentation.response.answer;

import com.moyeoit.context.deprecated.review.controller.response.QuestionResponse;
import com.moyeoit.context.deprecated.review.controller.response.v2.ReviewAnswerResponse;
import com.moyeoit.context.deprecated.review.domain.enums.AnswerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NumericAnswerResponse implements ReviewAnswerResponse {

    private Long id;
    private QuestionResponse question;
    private Double value;
    private AnswerType answerType;

}