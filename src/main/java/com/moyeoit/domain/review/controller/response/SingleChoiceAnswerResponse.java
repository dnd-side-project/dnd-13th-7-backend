package com.moyeoit.domain.review.controller.response;

import com.moyeoit.domain.app_user.service.dto.AppUserDto;
import com.moyeoit.domain.review.domain.BasicReviewDetail;
import com.moyeoit.domain.review.domain.PremiumReviewDetail;
import com.moyeoit.domain.review.domain.enums.AnswerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleChoiceAnswerResponse implements AnswerResponse {

    private Long id;
    private QuestionResponse question;
    private AppUserDto userDto;
    private Integer value;
    private AnswerType answerType;

    public static SingleChoiceAnswerResponse from(PremiumReviewDetail detail) {
        return new SingleChoiceAnswerResponse(detail.getId(),
                QuestionResponse.from(detail.getQuestion()),
                AppUserDto.of(detail.getAppUser()),
                Integer.valueOf(detail.getValue()),
                detail.getAnswerType()
        );
    }

    public static SingleChoiceAnswerResponse from(BasicReviewDetail detail) {
        return new SingleChoiceAnswerResponse(detail.getId(),
                QuestionResponse.from(detail.getQuestion()),
                AppUserDto.of(detail.getAppUser()),
                Integer.valueOf(detail.getValue()),
                detail.getAnswerType()
        );
    }

}
