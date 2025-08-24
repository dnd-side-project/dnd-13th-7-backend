package com.moyeoit.domain.review.controller.response;

import com.moyeoit.domain.app_user.service.dto.AppUserDto;
import com.moyeoit.domain.review.domain.PremiumReviewDetail;
import com.moyeoit.domain.review.domain.enums.AnswerType;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceAnswerResponse implements AnswerResponse {

    private Long id;
    private QuestionResponse question;
    private AppUserDto userDto;
    private List<Integer> value;
    private AnswerType answerType;

    public static MultipleChoiceAnswerResponse from(PremiumReviewDetail detail) {
        return new MultipleChoiceAnswerResponse(
                detail.getId(),
                QuestionResponse.from(detail.getQuestion()),
                AppUserDto.of(detail.getAppUser()),
                Arrays.stream(detail.getValue().split(","))
                        .map(Integer::parseInt)
                        .toList(),
                detail.getAnswerType()
        );
    }


}
