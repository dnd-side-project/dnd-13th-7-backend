package com.moyeoit.domain.review.controller.request.v2;

import com.moyeoit.domain.review.domain.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceAnswerV2 implements ReviewAnswerCreateRequest {

    private Long questionId;
    private QuestionType questionType;
    private List<Integer> value;
    private Integer sequence;

}