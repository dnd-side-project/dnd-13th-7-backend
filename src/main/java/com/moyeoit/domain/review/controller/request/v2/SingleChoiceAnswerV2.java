package com.moyeoit.domain.review.controller.request.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moyeoit.domain.review.domain.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleChoiceAnswerV2 implements ReviewAnswerCreateRequest {

    @JsonProperty("question_id")
    private Long questionId;

    @JsonProperty("question_type")
    private QuestionType questionType;

    private Integer value;

}
