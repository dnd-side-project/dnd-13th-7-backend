package com.moyeoit.context.review.presentation.response.answer;

import com.moyeoit.context.review.controller.response.QuestionResponse;
import com.moyeoit.context.review.controller.response.v2.ReviewAnswerResponse;
import com.moyeoit.context.review.domain.enums.AnswerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleSubjectiveAnswerResponse implements ReviewAnswerResponse {

    private Long id;
    private QuestionResponse question;
    private String value;
    private AnswerType answerType;

}
