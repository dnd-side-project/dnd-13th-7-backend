package com.moyeoit.domain.review.controller.response;

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

}