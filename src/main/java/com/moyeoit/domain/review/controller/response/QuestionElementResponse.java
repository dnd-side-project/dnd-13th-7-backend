package com.moyeoit.domain.review.controller.response;

import com.moyeoit.domain.review.domain.QuestionElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionElementResponse {

    private Long id;
    private String elementTitle;
    private Integer sequence;

    public static QuestionElementResponse from(QuestionElement element) {
        return new QuestionElementResponse(
                element.getId(),
                element.getElementTitle(),
                element.getSequence());
    }

}
