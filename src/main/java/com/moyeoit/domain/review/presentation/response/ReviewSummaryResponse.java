package com.moyeoit.domain.review.presentation.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewSummaryResponse {

    private String clubName;
    private Integer generation;
    private String jobName;
    private Double rate;
    private String title;
    private List<ReviewChoiceSummary> answerSummaries;
    private Long likeCount;
    private Long commentCount;

    public ReviewSummaryResponse(String clubName, Integer generation, String jobName, Double rate, String title, List<String> choiceSummaries, Long likeCount, Long commentCount) {
        this.clubName = clubName;
        this.generation = generation;
        this.jobName = jobName;
        this.rate = rate;
        this.title = title;
        this.answerSummaries = new ArrayList<>();
        for (int i = 0; i < choiceSummaries.size(); i += 2) {
            if (i + 1 < choiceSummaries.size()) {
                answerSummaries.add(new ReviewChoiceSummary(choiceSummaries.get(i), choiceSummaries.get(i + 1)));
            } else {
                break;
            }

        }
        this.likeCount = likeCount;
        this.commentCount = commentCount;

    }

}
