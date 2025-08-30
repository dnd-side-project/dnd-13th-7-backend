package com.moyeoit.domain.review.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionElementCreateRequest {

    private String elementTitle;

    @NotNull(message = "값은 필수입니다.")
    @Min(value = 1, message = "값은 최소 1 이상이어야 합니다.")
    private Integer sequence;

}
