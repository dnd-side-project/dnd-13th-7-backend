package com.moyeoit.domain.review.controller.request;

import com.moyeoit.domain.review.domain.enums.QuestionType;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreateRequest {

    @NotBlank
    private String title;

    private String subTitle;

    @NotBlank
    private QuestionType type;

    private List<QuestionElementCreateRequest> elements;

}