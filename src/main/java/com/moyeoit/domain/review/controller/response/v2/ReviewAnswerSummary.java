package com.moyeoit.domain.review.controller.response.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewAnswerSummary {

    private String questionTitleSummary;
    private String value;

}
