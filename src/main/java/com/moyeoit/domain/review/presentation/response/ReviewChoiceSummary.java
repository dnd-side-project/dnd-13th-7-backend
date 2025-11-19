package com.moyeoit.domain.review.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewChoiceSummary {

    private String questionTitleSummary;
    private String answerSummary;

}
