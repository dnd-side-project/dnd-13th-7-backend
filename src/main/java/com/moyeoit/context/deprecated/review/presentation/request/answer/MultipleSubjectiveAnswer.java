package com.moyeoit.context.deprecated.review.presentation.request.answer;

import com.moyeoit.context.deprecated.review.domain.enums.QuestionType;
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

    private Long questionId;
    private QuestionType questionType;
    private List<String> value;
    private Integer sequence;

}
