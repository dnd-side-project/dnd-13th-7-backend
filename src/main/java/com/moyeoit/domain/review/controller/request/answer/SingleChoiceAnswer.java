package com.moyeoit.domain.review.controller.request.answer;


import com.moyeoit.domain.review.domain.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleChoiceAnswer implements AnswerRequest {

    private Long questionId;
    private QuestionType questionType;
    private Integer value;
}
