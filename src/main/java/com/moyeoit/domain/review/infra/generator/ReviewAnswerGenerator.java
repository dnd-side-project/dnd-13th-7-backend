package com.moyeoit.domain.review.infra.generator;

import com.moyeoit.domain.review.domain.enums.AnswerType;
import com.moyeoit.domain.review.domain.model.Review;
import com.moyeoit.domain.review.domain.model.ReviewAnswer;
import com.moyeoit.domain.review.infra.util.ArrayConverter;
import com.moyeoit.domain.review.presentation.request.answer.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewAnswerGenerator {

    public List<ReviewAnswer> generate(Review review, List<ReviewAnswerCreateRequest> answers) {
        return answers.stream()
                .map(answer -> this.generate(review, answer))
                .toList();
    }

    public ReviewAnswer generate(Review review, ReviewAnswerCreateRequest answer) {
        if (answer instanceof SingleChoiceAnswer) {
            return generateSingleChoiceAnswer(review, (SingleChoiceAnswer) answer);
        }

        if (answer instanceof MultipleChoiceAnswer) {
            return generateMultipleChoiceAnswer(review, (MultipleChoiceAnswer) answer);
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

    /**
     * 단일 객관식 답변 생성
     */
    private ReviewAnswer generateSingleChoiceAnswer(Review review, SingleChoiceAnswer answer) {
        return ReviewAnswer.builder()
                .review(review)
                .reviewQuestionId(answer.getQuestionId())
                .value(String.valueOf(answer.getValue()))
                .valueType(AnswerType.INTEGER)
                .numericValue(null)
                .sequence(answer.getSequence())
                .build();
    }

    /**
     * 다중 객관식 답변 생성 (구분자: ,)
     */
    private ReviewAnswer generateMultipleChoiceAnswer(Review review, MultipleChoiceAnswer answer) {
        return ReviewAnswer.builder()
                .review(review)
                .reviewQuestionId(answer.getQuestionId())
                .value(ArrayConverter.toTextFromIntegerArray(answer.getValue(), ","))
                .valueType(AnswerType.ARRAY_INTEGER)
                .numericValue(null)
                .sequence(answer.getSequence())
                .build();
    }

    /**
     * 단일 주관식 답변 생성
     */
    private ReviewAnswer generateSingleSubjectiveAnswer(Review review, SingleSubjectiveAnswer answer) {
        return ReviewAnswer.builder()
                .review(review)
                .reviewQuestionId(answer.getQuestionId())
                .value(answer.getValue())
                .valueType(AnswerType.TEXT)
                .numericValue(null)
                .sequence(answer.getSequence())
                .build();
    }

    /**
     * 다중 주관식 답변 생성 (구분자 : |^|)
     */
    private ReviewAnswer generateMultipleSubjectiveAnswer(Review review, MultipleSubjectiveAnswer answer) {
        return ReviewAnswer.builder()
                .review(review)
                .reviewQuestionId(answer.getQuestionId())
                .value(ArrayConverter.toTextFromStringArray(answer.getValue(), "\\|\\^\\|"))
                .valueType(AnswerType.ARRAY_TEXT)
                .numericValue(null)
                .sequence(answer.getSequence())
                .build();
    }

    /**
     * 수치화 답변 생성
     */
    private ReviewAnswer generateNumericAnswer(Review review, NumericAnswer answer) {
        return ReviewAnswer.builder()
                .review(review)
                .reviewQuestionId(answer.getQuestionId())
                .value(answer.getValue().toString())
                .valueType(AnswerType.DOUBLE)
                .numericValue(answer.getValue())
                .sequence(answer.getSequence())
                .build();
    }


}