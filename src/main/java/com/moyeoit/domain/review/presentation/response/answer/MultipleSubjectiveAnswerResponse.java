package com.moyeoit.domain.review.presentation.response.answer;

import com.moyeoit.domain.review.controller.response.QuestionResponse;
import com.moyeoit.domain.review.controller.response.v2.ReviewAnswerResponse;
import com.moyeoit.domain.review.domain.enums.AnswerType;
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