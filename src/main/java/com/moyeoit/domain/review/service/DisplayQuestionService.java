package com.moyeoit.domain.review.service;

import com.moyeoit.domain.review.controller.request.DisplayQuestionCreateRequest;
import com.moyeoit.domain.review.controller.response.DisplayQuestionResponse;
import com.moyeoit.domain.review.controller.response.DisplayQuestionResponses;
import com.moyeoit.domain.review.domain.DisplayQuestion;
import com.moyeoit.domain.review.domain.Question;
import com.moyeoit.domain.review.domain.ReviewCategory;
import com.moyeoit.domain.review.domain.ReviewType;
import com.moyeoit.domain.review.repository.DisplayQuestionRepository;
import com.moyeoit.domain.review.repository.QuestionRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.QuestionErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DisplayQuestionService {

    private final DisplayQuestionRepository displayQuestionRepository;
    private final QuestionRepository questionRepository;

    public DisplayQuestionResponses getDisplayQuestions(ReviewType type,
                                                        ReviewCategory category) {
        List<DisplayQuestion> questions = displayQuestionRepository.findByTypeAndCategory(type, category);

        if (questions.isEmpty()) {
            throw new AppException(QuestionErrorCode.NOT_FOUND);
        }

        List<DisplayQuestionResponse> response = questions.stream()
                .map(DisplayQuestionResponse::from)
                .toList();

        return new DisplayQuestionResponses(response);
    }

    public DisplayQuestionResponse createDisplayQuestion(DisplayQuestionCreateRequest req) {
        Question question = questionRepository.findById(req.getQuestionId())
                .orElseThrow(() -> new AppException(QuestionErrorCode.NOT_FOUND));

        DisplayQuestion displayQuestion = DisplayQuestion.builder()
                .question(question)
                .reviewType(req.getReviewType())
                .reviewCategory(req.getReviewCategory())
                .sort(req.getSort())
                .build();

        DisplayQuestion savedDisplayQuestion = displayQuestionRepository.save(displayQuestion);
        return DisplayQuestionResponse.from(savedDisplayQuestion);
    }

}
