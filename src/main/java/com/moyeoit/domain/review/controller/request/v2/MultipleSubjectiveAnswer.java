package com.moyeoit.domain.review.controller.request.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MultipleSubjectiveAnswer implements ReviewAnswerCreateRequest {

    @JsonProperty("question_id")
    private Long questionId;

    @JsonProperty("question_type")
    private QuestionType questionType;

    private List<String> value;

    private Integer sequence;

}
