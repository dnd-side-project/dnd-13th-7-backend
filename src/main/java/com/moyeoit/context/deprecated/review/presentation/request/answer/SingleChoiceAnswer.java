package com.moyeoit.context.deprecated.review.presentation.request.answer;

import com.moyeoit.context.deprecated.review.domain.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleChoiceAnswer implements ReviewAnswerCreateRequest {


    private Long questionId;
    private QuestionType questionType;
    private Integer value;
    private Integer sequence;

}
