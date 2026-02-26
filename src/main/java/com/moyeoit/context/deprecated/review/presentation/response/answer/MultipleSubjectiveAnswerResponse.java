package com.moyeoit.context.deprecated.review.presentation.response.answer;

import com.moyeoit.context.deprecated.review.controller.response.QuestionResponse;
import com.moyeoit.context.deprecated.review.controller.response.v2.ReviewAnswerResponse;
import com.moyeoit.context.deprecated.review.domain.enums.AnswerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MultipleSubjectiveAnswerResponse implements ReviewAnswerResponse {

    private Long id;
    private QuestionResponse question;
    private List<String> value;
    private AnswerType answerType;

}