package com.moyeoit.domain.review.service;

import com.moyeoit.domain.review.controller.request.QuestionCreateRequest;
import com.moyeoit.domain.review.controller.request.QuestionElementCreateRequest;
import com.moyeoit.domain.review.controller.response.QuestionResponse;
import com.moyeoit.domain.review.domain.Question;
import com.moyeoit.domain.review.domain.QuestionElement;
import com.moyeoit.domain.review.domain.enums.QuestionType;
import com.moyeoit.domain.review.repository.QuestionElementRepository;
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
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionElementRepository questionElementRepository;

    public QuestionResponse getQuestionById(Long id) {
        Question question = questionRepository.findByIdWithQuestionElements(id)
                .orElseThrow(() -> new AppException(QuestionErrorCode.NOT_FOUND));

        return QuestionResponse.from(question);
    }

    public QuestionResponse createQuestion(QuestionCreateRequest request) {
        if (!checkElementSort(request.getElements())) {
            throw new AppException(QuestionErrorCode.INVALID_SORT);
        }

        Question question = createQuestion(request.getTitle(), request.getSubTitle(), request.getType());
        Question savedQuestion = questionRepository.save(question);

        List<QuestionElementCreateRequest> elementRequests = request.getElements();
        List<QuestionElement> questionElements = elementRequests.stream()
                .map(elementRequest -> createQuestionElement(elementRequest, savedQuestion))
                .toList();

        // TODO : Bulk Insert
        questionElementRepository.saveAll(questionElements);

        return QuestionResponse.from(question, questionElements);
    }

    private Question createQuestion(String title, String subTitle, QuestionType type) {
        return Question.builder()
                .title(title)
                .subtitle(subTitle)
                .type(type)
                .build();
    }

    private QuestionElement createQuestionElement(QuestionElementCreateRequest request, Question question) {
        if (QuestionType.SUBJECTIVE.equals(question.getType())) { // SUBJECTIVE CASE
            return QuestionElement.builder()
                    .question(question)
                    .elementTitle(question.getTitle())
                    .sequence(1)
                    .build();
        }

        return QuestionElement.builder()
                .question(question)
                .elementTitle(request.getElementTitle())
                .sequence(request.getSequence())
                .build();
    }

    private boolean checkElementSort(List<QuestionElementCreateRequest> elements) {
        if (elements == null || elements.isEmpty()) {
            return false;
        }

        List<Integer> sorts = elements.stream()
                .map(QuestionElementCreateRequest::getSequence)
                .sorted()
                .toList();

        for (int i = 0; i < sorts.size(); i++) {
            if (sorts.get(i) != i + 1) {
                return false;
            }
        }
        return true;
    }

}
