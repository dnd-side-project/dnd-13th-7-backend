package com.moyeoit.domain.review.controller.response;

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
public class PremiumReviewDetailResponse {

    private Long id;
    private QuestionResponse question;
    private AppUserDto userDto;
    private String value;
    private AnswerType answerType;

    public static PremiumReviewDetailResponse from(PremiumReviewDetail detail) {
        return new PremiumReviewDetailResponse(detail.getId(),
                QuestionResponse.from(detail.getQuestion()),
                AppUserDto.of(detail.getAppUser()),
                detail.getValue(),
                detail.getAnswerType()
        );
    }

}
