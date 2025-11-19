package com.moyeoit.domain.review.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewAnswerSummaryResponse {

    private String title;
    private String answer;

}
