package com.moyeoit.domain.review.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewOptionSummaryDto {

    private String title;
    private String description;
    private Integer sequence;

}
