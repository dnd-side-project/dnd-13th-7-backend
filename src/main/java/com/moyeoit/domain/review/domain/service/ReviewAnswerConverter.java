package com.moyeoit.domain.review.domain.service;

import com.moyeoit.domain.review.controller.response.v2.OriginalReviewAnswer;
import com.moyeoit.domain.review.controller.response.v2.ReviewAnswerResponse;
import com.moyeoit.domain.review.domain.enums.AnswerType;
import com.moyeoit.domain.review.infra.util.ArrayConverter;
import com.moyeoit.domain.review.presentation.response.answer.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReviewAnswerConverter {

    public List<ReviewAnswerResponse> toResponses(List<OriginalReviewAnswer> originalReviewAnswers) {
        return originalReviewAnswers.stream()
                .map(this::toResponse)
                .toList();
    }

    public ReviewAnswerResponse toResponse(OriginalReviewAnswer answer) {
        if (answer.getAnswerType().equals(AnswerType.INTEGER)) {
            return new SingleChoiceAnswerResponse(
                    answer.getId(),
                    answer.getQuestion(),
                    Integer.valueOf(answer.getValue()),
                    answer.getAnswerType()
            );
        }

        if (answer.getAnswerType().equals(AnswerType.DOUBLE)) {
            return new NumericAnswerResponse(
                    answer.getId(),
                    answer.getQuestion(),
                    Double.valueOf(answer.getValue()),
                    answer.getAnswerType()
            );
        }

        if (answer.getAnswerType().equals(AnswerType.ARRAY_INTEGER)) {
            return new MultipleChoiceAnswerResponse(
                    answer.getId(),
                    answer.getQuestion(),
                    ArrayConverter.toIntegerArray(answer.getValue(), ","),
                    answer.getAnswerType()
            );
        }

        if (answer.getAnswerType().equals(AnswerType.TEXT)) {
            return new SingleSubjectiveAnswerResponse(
                    answer.getId(),
                    answer.getQuestion(),
                    answer.getValue(),
                    answer.getAnswerType()
            );
        }

        if (answer.getAnswerType().equals(AnswerType.ARRAY_TEXT)) {
            return new MultipleSubjectiveAnswerResponse(
                    answer.getId(),
                    answer.getQuestion(),
                    ArrayConverter.toStringArray(answer.getValue(), "\\|\\^\\|"),
                    answer.getAnswerType()
            );
        }

        return null;
    }


}
