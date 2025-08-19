package com.moyeoit.domain.review.controller.request;

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
    private Integer sequence;
    
}
