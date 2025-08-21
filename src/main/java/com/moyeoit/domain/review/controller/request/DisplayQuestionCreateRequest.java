package com.moyeoit.domain.review.controller.request;

import com.moyeoit.domain.review.domain.ReviewCategory;
import com.moyeoit.domain.review.domain.ReviewType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisplayQuestionCreateRequest {

    @NotNull
    private Long questionId;

    @NotNull
    private ReviewType reviewType;

    @NotNull
    private ReviewCategory reviewCategory;

    @NotNull
    private Integer sort;

}
