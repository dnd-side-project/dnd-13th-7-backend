package com.moyeoit.domain.review.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewQuestionSummaryDto {

    private Long questionId;
    private String title;
    private List<ReviewOptionSummaryDto> options;

}