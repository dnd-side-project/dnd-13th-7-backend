package com.moyeoit.domain.review.controller.response;

import com.moyeoit.domain.review.domain.BasicReviewDetail;
import com.moyeoit.domain.review.domain.PremiumReviewDetail;
import com.moyeoit.domain.review.domain.enums.AnswerType;
import com.moyeoit.domain.user.service.dto.AppUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectiveAnswerResponse implements AnswerResponse {

    private Long id;
    private QuestionResponse question;
    private AppUserDto userDto;
    private String value;
    private AnswerType answerType;

    public static SubjectiveAnswerResponse from(PremiumReviewDetail detail) {
        return new SubjectiveAnswerResponse(
                detail.getId(),
                QuestionResponse.from(detail.getQuestion()),
                AppUserDto.of(detail.getAppUser()),
                detail.getValue(),
                detail.getAnswerType()
        );
    }

    public static SubjectiveAnswerResponse from(BasicReviewDetail detail) {
        return new SubjectiveAnswerResponse(
                detail.getId(),
                QuestionResponse.from(detail.getQuestion()),
                AppUserDto.of(detail.getAppUser()),
                detail.getValue(),
                detail.getAnswerType()
        );
    }

}
