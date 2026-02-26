package com.moyeoit.context.deprecated.review.controller.response;

import com.moyeoit.context.deprecated.review.domain.enums.QuestionType;
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

}