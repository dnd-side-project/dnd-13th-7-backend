package com.moyeoit.domain.review.controller.request.answer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.moyeoit.domain.review.domain.enums.QuestionType;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = As.EXISTING_PROPERTY,
        property = "questionType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SubjectiveAnswer.class, name = "SUBJECTIVE"),
        @JsonSubTypes.Type(value = SingleChoiceAnswer.class, name = "SINGLE_CHOICE"),
        @JsonSubTypes.Type(value = MultipleChoiceAnswer.class, name = "MULTIPLE_CHOICE")
})
public interface AnswerRequest {

    Long getQuestionId();

    QuestionType getQuestionType();
}
