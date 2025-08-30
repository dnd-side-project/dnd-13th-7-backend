package com.moyeoit.domain.review.controller.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisplayQuestionResponses {

    private List<DisplayQuestionResponse> questions;
    
}
