package com.moyeoit.domain.review.controller.response;

import com.moyeoit.domain.review.domain.Question;
import com.moyeoit.domain.review.domain.QuestionElement;
import com.moyeoit.domain.review.domain.enums.QuestionType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

    private Long id;
    private String title;
    private String subTitle;
    private QuestionType type;
    private List<QuestionElementResponse> elements;

    public static QuestionResponse from(Question question, List<QuestionElement> elements) {
        return new QuestionResponse(question.getId(),
                question.getTitle(),
                question.getSubtitle(),
                question.getType(),
                elements.stream()
                        .map(QuestionElementResponse::from)
                        .toList()
        );
    }

    public static QuestionResponse from(Question question) {
        List<QuestionElement> elements = question.getQuestionElements();
        return new QuestionResponse(question.getId(),
                question.getTitle(),
                question.getSubtitle(),
                question.getType(),
                elements.stream()
                        .map(QuestionElementResponse::from)
                        .toList()
        );
    }


}