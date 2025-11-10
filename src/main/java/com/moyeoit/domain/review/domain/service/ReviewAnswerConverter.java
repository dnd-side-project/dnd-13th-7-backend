package com.moyeoit.domain.review.domain.service;

import com.moyeoit.domain.review.controller.request.v2.*;
import com.moyeoit.domain.review.controller.response.v2.*;
import com.moyeoit.domain.review.domain.enums.AnswerType;
import com.moyeoit.domain.review.domain.v2.Review;
import com.moyeoit.domain.review.domain.v2.ReviewAnswer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                    stringToIntegerArray(answer.getValue()),
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
                stringToStringArray(answer.getValue()),
                answer.getAnswerType()
            );
        }

        return null;
    }

    public List<ReviewAnswer> generate(Review review, List<ReviewAnswerCreateRequest> answers) {
        return answers.stream()
                .map(answer -> this.generate(review, answer))
                .toList();
    }

    public ReviewAnswer generate(Review review, ReviewAnswerCreateRequest answer) {
        if (answer instanceof SingleChoiceAnswerV2) {
            return generateSingleChoiceAnswer(review, (SingleChoiceAnswerV2) answer);
        }

        if (answer instanceof MultipleChoiceAnswerV2) {
            return generateMultipleChoiceAnswer(review, (MultipleChoiceAnswerV2) answer);
        }

        if (answer instanceof SingleSubjectiveAnswer) {
            return generateSingleSubjectiveAnswer(review, (SingleSubjectiveAnswer) answer);
        }

        if (answer instanceof MultipleSubjectiveAnswer) {
            return generateMultipleSubjectiveAnswer(review, (MultipleSubjectiveAnswer) answer);
        }

        if (answer instanceof NumericAnswer) {
            return generateNumericAnswer(review, (NumericAnswer) answer);
        }
        return null;
    }


    private ReviewAnswer generateSingleChoiceAnswer(Review review, SingleChoiceAnswerV2 answer) {
        return ReviewAnswer.builder()
                .review(review)
                .reviewQuestionId(answer.getQuestionId())
                .value(String.valueOf(answer.getValue()))
                .valueType(AnswerType.INTEGER)
                .numericValue(null)
                .build();
    }

    private ReviewAnswer generateMultipleChoiceAnswer(Review review, MultipleChoiceAnswerV2 answer) {
        return ReviewAnswer.builder()
                .review(review)
                .reviewQuestionId(answer.getQuestionId())
                .value(generateIntegerArrayValue(answer.getValue()))
                .valueType(AnswerType.ARRAY_INTEGER)
                .numericValue(null)
                .build();
    }

    private ReviewAnswer generateSingleSubjectiveAnswer(Review review, SingleSubjectiveAnswer answer) {
        return ReviewAnswer.builder()
                .review(review)
                .reviewQuestionId(answer.getQuestionId())
                .value(answer.getValue())
                .valueType(AnswerType.TEXT)
                .numericValue(null)
                .build();
    }

    private ReviewAnswer generateMultipleSubjectiveAnswer(Review review, MultipleSubjectiveAnswer answer) {
        return ReviewAnswer.builder()
                .review(review)
                .reviewQuestionId(answer.getQuestionId())
                .value(generateStringArrayValue(answer.getValue()))
                .valueType(AnswerType.ARRAY_TEXT)
                .numericValue(null)
                .build();
    }

    private ReviewAnswer generateNumericAnswer(Review review, NumericAnswer answer) {
        return ReviewAnswer.builder()
                .review(review)
                .reviewQuestionId(answer.getQuestionId())
                .value(answer.getValue().toString())
                .valueType(AnswerType.DOUBLE)
                .numericValue(answer.getValue())
                .build();
    }

    private String generateStringArrayValue(List<String> values) {
        return values.stream()
                .collect(Collectors.joining("|^|"));
    }

    private String generateIntegerArrayValue(List<Integer> values) {
        return values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    private List<String> stringToStringArray(String value) {
        if (value == null || value.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(value.split("\\|\\^\\|"));
    }

    private List<Integer> stringToIntegerArray(String value) {
        if (value == null || value.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(value.split(","))
                .map(Integer::valueOf)
                .toList();
    }


}
