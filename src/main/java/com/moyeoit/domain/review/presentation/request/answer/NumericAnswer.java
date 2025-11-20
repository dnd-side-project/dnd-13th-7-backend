package com.moyeoit.domain.review.presentation.request.answer;

import com.moyeoit.domain.review.domain.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NumericAnswer implements ReviewAnswerCreateRequest {

    private Long questionId;
    private QuestionType questionType;
    private Double value;
    private Integer sequence;

}