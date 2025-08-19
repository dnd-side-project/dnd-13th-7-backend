package com.moyeoit.domain.review.controller.request;

import com.moyeoit.domain.review.domain.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectiveAnswer implements AnswerRequest {

    private Long questionId;
    private QuestionType questionType;
    private String value;

}