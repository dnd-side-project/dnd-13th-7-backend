package com.moyeoit.domain.review.presentation.request.answer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moyeoit.domain.review.domain.enums.QuestionType;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "question_type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SingleSubjectiveAnswer.class, name = "SINGLE_SUBJECTIVE"),
        @JsonSubTypes.Type(value = MultipleSubjectiveAnswer.class, name = "MULTIPLE_SUBJECTIVE"),
        @JsonSubTypes.Type(value = SingleChoiceAnswer.class, name = "SINGLE_CHOICE"),
        @JsonSubTypes.Type(value = MultipleChoiceAnswer.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = NumericAnswer.class, name = "NUMERIC"),
})
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface ReviewAnswerCreateRequest {

    QuestionType getQuestionType();

    Long getQuestionId();

    Integer getSequence();

}
